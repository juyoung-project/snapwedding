package kr.or.wds.project.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
@Tag(name = "OAuth", description = "OAuth2 소셜 로그인 API")
public class OAuthController {


    @Operation(summary = "OAuth 로그인 실패", description = "네이버 OAuth 로그인 실패")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "로그인 실패")
    })
    @GetMapping("/failure")
    public ResponseEntity<String> oauthFailure() {
        return ResponseEntity.badRequest().body("OAuth 로그인에 실패했습니다.");
    }

    @Operation(summary = "OAuth 정보 확인", description = "현재 OAuth 사용자 정보 확인")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getOAuthInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            return ResponseEntity.ok(oAuth2User.getAttributes());
        }

        return ResponseEntity.ok(Map.of("message", "OAuth 사용자가 아닙니다."));
    }

}