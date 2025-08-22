package kr.or.wds.project.dto.helper;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FileDto {

    @Schema
    private Long id;

    @Schema(description = "파일경로")
    private String filePath;

    @Schema(description = "파일명", example = "report.pdf")
    private String fileNm;

    @Schema(description = "파일확장자")
    private String fileExtension;

    @Schema(description = "파일저장용")
    private String fileStoreNm;

    @Schema(description = "파일크기")
    private long fileSize;

    @Schema(description = "파일 MIME")
    private String fileMimeType;

}
