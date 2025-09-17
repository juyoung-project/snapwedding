package kr.or.wds.project.helper;

import jakarta.transaction.Transactional;
import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.common.records.FileAfterContext;
import kr.or.wds.project.dto.helper.FileDownloadDto;
import kr.or.wds.project.entity.FileMasterEntity;
import kr.or.wds.project.exception.CustomException;
import kr.or.wds.project.exception.ExceptionType;
import kr.or.wds.project.properties.FileStorageProperties;
import kr.or.wds.project.repository.FileMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileHelper {

    private final FileMasterRepository fileMasterRepository;
    private final List<UploadAfterProcessor> processors;
    private final List<FileStorageHelperIf> storageAdapters; // 모든 구현체가 자동 주입
    private final FileStorageProperties storageProperties;   // 설정값 주입

    @Transactional
    public List<Long> upload(FileUploadDomainType domain,
                         List<MultipartFile> fileList,
                         Long aggregateId,
                         Long uploadUserId
    ) {
        // 1) 파일 저장
        var provider = storageProperties.getProvider();
        var storageHelper = storageAdapters.stream()
                .filter(a -> a.getStorageType() == provider)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No FileStorageHelper for provider: " + provider));

        List<Long> fileId = storageHelper.storeFile(fileList, domain);

        // 2) 도메인 프로세서 존재 강제
        boolean exists = processors.stream().anyMatch(p -> p.supports(domain));
        if (!exists) {
            throw new IllegalStateException("No UploadAfterProcessor registered for domain: " + domain);
        }

        // 3) 트랜잭션 커밋 이후 실행 강제
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new IllegalStateException("Upload requires an active transaction for safe after-process.");
        }

        var ctx = new FileAfterContext(domain, aggregateId, fileId, uploadUserId);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 각 도메인의 프로세서 실행
                processors.stream()
                        .filter(p -> p.supports(domain))
                        .forEach(p -> {
                            try {
                                p.process(ctx);
                            } catch (Exception ex) {
                                // 실패 정책: 로깅 후 계속 or 알림/재시도 큐 적재
                                // 필요 시 실패 전파: throw new RuntimeException(ex);
                            }
                        });
            }
        });
        return fileId;
    }

    public FileDownloadDto downloadFile(Long fileId) {
        FileMasterEntity fileMasterEntity = fileMasterRepository.findById(fileId).orElseThrow(() -> new CustomException(ExceptionType.FILE_NOT_FOUND));
        Resource resource = this.getResource(fileMasterEntity.getFilePath(), fileMasterEntity.getFileStoreNm());
        FileDownloadDto dto = new FileDownloadDto();
        dto.setFileNm(encodeFileName(fileMasterEntity.getFileNm()));
        dto.setResource(resource);
        dto.setMimeType(fileMasterEntity.getFileMimeType());
        return dto;
    }

    public String encodeFileName(String fileName) {
        String encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        return "attachment; filename=\"%s\"".formatted(encoded);
    }

    private Resource getResource(String path, String storeFileNm) {
        Path filePath = Paths.get(storageProperties.getPath())
                .resolve(path)
                .resolve(storeFileNm);
        Resource resource = new FileSystemResource(filePath.toFile());

        if (!resource.exists() || !resource.isReadable()) {
            throw new CustomException(ExceptionType.FILE_NOT_FOUND);
        }

        return resource;
    }
}
