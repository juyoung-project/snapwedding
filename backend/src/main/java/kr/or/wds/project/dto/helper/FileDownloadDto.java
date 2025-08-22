package kr.or.wds.project.dto.helper;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class FileDownloadDto {

    private String fileNm;
    private String mimeType;
    private Resource resource;

}
