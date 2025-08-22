package kr.or.wds.project.factory;

import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.dto.helper.FileDto;
import kr.or.wds.project.entity.FileMasterEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
public class FileFactory {

    public List<FileMasterEntity> getListFileEntity(List<FileDto> dtoList) {

        return dtoList.stream()
                .map(dto -> FileMasterEntity.builder()
                        .fileNm(dto.getFileNm())
                        .fileExtension(dto.getFileExtension())
                        .fileSize(dto.getFileSize())
                        .filePath(dto.getFilePath())
                        .fileStoreNm(dto.getFileStoreNm())
                        .fileMimeType(dto.getFileMimeType())
                        .build())
                .toList();

    };

    public FileDto buildFileDto(MultipartFile file, FileUploadDomainType domain) {
        String originalFilename = sanitizeOriginalFilename(file.getOriginalFilename());
        String ext = extractExtension(originalFilename);
        String systemName = buildSystemName(ext);
        String datedPath = buildDatedPath();
        String contentType = safeContentType(file.getContentType());

        FileDto dto = new FileDto();
        dto.setFileExtension(ext);
        dto.setFileNm(originalFilename);
        dto.setFilePath(domain+"/"+ datedPath);
        dto.setFileSize(file.getSize());
        dto.setFileStoreNm(systemName);
        dto.setFileMimeType(contentType);
        return dto;
    }

    private String sanitizeOriginalFilename(String original) {
        String name = StringUtils.getFilename(original);
        if (!StringUtils.hasText(name)) return "unknown";
        return name.strip();
    }

    private String extractExtension(String filename) {
        if (!StringUtils.hasText(filename)) return "";
        String ext = StringUtils.getFilenameExtension(filename);
        return ext != null ? ext.toLowerCase(Locale.ROOT) : "";
    }

    private String buildSystemName(String ext) {
        String uuid = UUID.randomUUID().toString();
        return StringUtils.hasText(ext) ? uuid + "." + ext : uuid;
    }

    private String buildDatedPath() {
        return LocalDate.now().toString().replace("-", "/") + "/";
    }

    private String safeContentType(String contentType) {
        return StringUtils.hasText(contentType) ? contentType : "application/octet-stream";
    }

}
