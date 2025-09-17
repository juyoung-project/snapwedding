package kr.or.wds.project.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.wds.project.common.JwtTokenProvider;
import kr.or.wds.project.dto.response.TokenResponseDto;
import kr.or.wds.project.entity.UserEntity;
import kr.or.wds.project.repository.UserRepository;
import kr.or.wds.project.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 환경에 따른 기본 쿠키 설정
    private static final String COOKIE_PATH = "/";
    private static final String COOKIE_DOMAIN = null; // 운영 시 ".example.com" 처럼 지정 가능
    private static final String SAMESITE = "Lax";     // 필요 시 "None" (HTTPS + Secure 필수)
    private static final boolean HTTP_ONLY = true;

    private static final int ACCESS_TOKEN_MAX_AGE = 60 * 15;        // 15분
    private static final int REFRESH_TOKEN_MAX_AGE = 60 * 60 * 24;  // 1일 (예시)


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = (String) oAuth2User.getAttributes().get("email");

        Optional<UserEntity> optionalMember = userRepository.findByEmail(email);
        if (optionalMember.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "회원 정보를 찾을 수 없습니다.");
            return;
        }

        UserEntity member = optionalMember.get();

        TokenResponseDto tokenResponseDto = jwtTokenProvider.generateToken(member.getEmail(), member.getRole().getValue());

        CookieUtils.deleteCookie(response, "ACCESS_TOKEN", false, SAMESITE, COOKIE_PATH, COOKIE_DOMAIN);
        CookieUtils.deleteCookie(response, "REFRESH_TOKEN", false, SAMESITE, COOKIE_PATH, COOKIE_DOMAIN);

        // 쿠키로 토큰 내려주기
        CookieUtils.addCookie(
                response,
                "access_token",
                tokenResponseDto.getAccessToken(),
                ACCESS_TOKEN_MAX_AGE,
                HTTP_ONLY,
                false,
                SAMESITE,
                COOKIE_PATH,
                COOKIE_DOMAIN
        );

        CookieUtils.addCookie(
                response,
                "refresh_token",
                tokenResponseDto.getRefreshToken(),
                REFRESH_TOKEN_MAX_AGE,
                HTTP_ONLY,
                false,
                SAMESITE,
                COOKIE_PATH,
                COOKIE_DOMAIN
        );

        // React 브리지 페이지로 리디렉션
        response.sendRedirect("http://localhost:3002/dashboard");
    }

}
