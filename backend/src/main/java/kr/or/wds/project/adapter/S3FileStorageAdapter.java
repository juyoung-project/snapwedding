package kr.or.wds.project.adapter;

import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.common.enums.StorageType;
import kr.or.wds.project.helper.FileStorageHelperIf;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class S3FileStorageAdapter implements FileStorageHelperIf {

    @Override
    public  List<Long>  storeFile(List<MultipartFile> fileList, FileUploadDomainType domain) {
        return null;
    }

    @Override
    public void deleteFile(String fileId) {

    }

    @Override
    public StorageType getStorageType() {
        return StorageType.S3;
    }
}
