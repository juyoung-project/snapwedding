package kr.or.wds.project.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.wds.project.dto.helper.FileDownloadDto;
import kr.or.wds.project.helper.FileHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "파일 다운로드 전용", description = "파일 다운로드 전용 API")
public class FileController {
    private final FileHelper fileHelper;

    @Operation(summary = "첨부파일 다운로드")
    @ApiResponse(responseCode = "200", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/octet-stream"), description = "파일 다운로드 성공")
    @GetMapping("/file-download/{fileId}")
    public ResponseEntity<Resource> downloadFile(
            @Parameter(description = "파일 id")
            @PathVariable Long fileId
    ) {
        FileDownloadDto dto = fileHelper.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dto.getMimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, dto.getFileNm())
                .body(dto.getResource());
    }
}
