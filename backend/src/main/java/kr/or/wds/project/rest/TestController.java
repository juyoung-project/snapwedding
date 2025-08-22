package kr.or.wds.project.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.dto.helper.FileDownloadDto;
import kr.or.wds.project.helper.FileHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Tag(name = "테스트", description = "테스트용 API")
public class TestController {

    private final FileHelper fileHelper;

    @Operation(summary = "기본 테스트", description = "서버가 정상 작동하는지 확인")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("서버가 정상 작동 중입니다!");
    }

    @Operation(summary = "인증 테스트", description = "JWT 토큰이 유효한지 확인")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/auth")
    public ResponseEntity<String> authTest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok("인증된 사용자: " + authentication.getName());
        }
        return ResponseEntity.ok("인증되지 않은 사용자");
    }

    @Operation(summary = "OAuth 로그인 링크", description = "네이버 OAuth 로그인 링크")
    @GetMapping("/oauth-link")
    public ResponseEntity<String> getOAuthLink() {
        String oauthUrl = "http://localhost:8090/oauth2/authorization/naver";
        return ResponseEntity.ok("네이버 로그인 링크: " + oauthUrl);
    }
    
    @Operation(summary = "TEST FILE UPLOAD", description = "파일 업로드 테스트")
    @PostMapping(value = "/file-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Long>> uploadFiles(@RequestPart(required = false) List<MultipartFile> files) {
        List<Long> fileIds = fileHelper.upload(FileUploadDomainType.PORTFOLIO, files, 1L, 1L);
        return ResponseEntity.ok(fileIds);
    }

    @Operation(summary = "게시판 상세 첨부파일 다운로드")
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
