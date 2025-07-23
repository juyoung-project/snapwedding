package kr.or.wds.project.service;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.wds.project.common.JwtTokenProvider;
import kr.or.wds.project.common.UserRole;
import kr.or.wds.project.common.UserStatus;
import kr.or.wds.project.dto.request.LoginRequest;
import kr.or.wds.project.dto.request.SignUpRequest;
import kr.or.wds.project.dto.response.TokenResponseDto;
import kr.or.wds.project.entity.UserEntity;
import kr.or.wds.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService  {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    
    public TokenResponseDto login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        TokenResponseDto tokenResponse = jwtTokenProvider.generateToken(request.getEmail(), authentication.getAuthorities().toString());
        return tokenResponse;
    }

    public void signup(SignUpRequest signUpDto) {
        UserEntity member = UserEntity.builder()
            .email(signUpDto.getEmail())
            .password(passwordEncoder.encode(signUpDto.getPassword()))
            .name(signUpDto.getName())
            .nickname(signUpDto.getNickname())
            .phone(signUpDto.getPhone())
            .birthDate(signUpDto.getBirthDate())
            .weddingDate(signUpDto.getWeddingDate())
            .oauthProvider("local")
            .oauthId(UUID.randomUUID().toString())
            .role(UserRole.USER)
            .status(UserStatus.ACTIVE)
            .build();
        userRepository.save(member);
    }

}
