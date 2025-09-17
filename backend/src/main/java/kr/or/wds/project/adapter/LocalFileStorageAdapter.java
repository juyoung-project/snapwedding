package kr.or.wds.project.adapter;

import jakarta.transaction.Transactional;
import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.common.enums.StorageType;
import kr.or.wds.project.dto.helper.FileDto;
import kr.or.wds.project.entity.FileMasterEntity;
import kr.or.wds.project.factory.FileFactory;
import kr.or.wds.project.helper.FileStorageHelperIf;
import kr.or.wds.project.properties.FileStorageProperties;
import kr.or.wds.project.repository.FileMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LocalFileStorageAdapter implements FileStorageHelperIf {

    private final FileMasterRepository fileMasterRepository;
    private final FileFactory fileFactory;
    private final FileStorageProperties fileStorageProperties;

    @Override
    @Transactional
    public List<Long> storeFile(List<MultipartFile> fileList, FileUploadDomainType domain) {
        List<FileDto> fileDtoList = new ArrayList<>();
        for (MultipartFile file : fileList) {
            if (file.isEmpty()) continue;
            FileDto dto = fileFactory.buildFileDto(file, domain);
            fileDtoList.add(dto);
            this.savePhysicallyFile(file, dto);
        }

        if (fileDtoList.isEmpty()) return null;

        return this.saveToDatabase(fileDtoList);
    }


    @Override
    public void deleteFile(String fileId) {

    }

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }

    private void savePhysicallyFile(MultipartFile file, FileDto dto) {
        File dir = new File(fileStorageProperties.getPath(), dto.getFilePath());
        if (!dir.exists()) {
            if (!dir.mkdirs()){
                throw new IllegalStateException("Directory creation failed.");
            }
        }
        File destFile = new java.io.File(dir, dto.getFileStoreNm());
        try {
            file.transferTo(destFile);
        } catch (Exception e) {
            throw new IllegalStateException("File upload failed.", e);
        }
    }

    private List<Long> saveToDatabase(List<FileDto> dtoList) {
        List<FileMasterEntity> entities = fileFactory.getListFileEntity(dtoList);
        List<FileMasterEntity> fileMasterEntities = fileMasterRepository.saveAll(entities);
        return fileMasterEntities.stream().map(FileMasterEntity::getId).toList();
    }

}
