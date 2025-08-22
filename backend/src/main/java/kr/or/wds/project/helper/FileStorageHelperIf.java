package kr.or.wds.project.helper;

import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.common.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageHelperIf {

    List<Long>  storeFile(List<MultipartFile> fileList, FileUploadDomainType domain);

    void deleteFile(String fileId);

    StorageType getStorageType();

}
