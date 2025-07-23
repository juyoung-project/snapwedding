package kr.or.wds.project.handler;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.wds.project.common.JwtTokenProvider;
import kr.or.wds.project.dto.response.TokenResponseDto;
import kr.or.wds.project.entity.UserEntity;
import kr.or.wds.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

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

        // 토큰을 세션에 저장
        request.getSession().setAttribute("ACCESS_TOKEN", tokenResponseDto.getAccessToken());
        request.getSession().setAttribute("REFRESH_TOKEN", tokenResponseDto.getRefreshToken());
    
        // React 브리지 페이지로 리디렉션
        response.sendRedirect("http://localhost:3002/bridge");
    }

}
