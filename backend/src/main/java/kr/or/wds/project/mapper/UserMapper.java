package kr.or.wds.project.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.wds.project.dto.request.SignUpRequest;
import kr.or.wds.project.entity.UserEntity;
import kr.or.wds.project.common.UserRole;
import kr.or.wds.project.common.Status;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserEntity toEntity(SignUpRequest dto) {
        return UserEntity.builder()
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .name(dto.getName())
            .nickname(dto.getNickname())
            .phone(dto.getPhone())
            .birthDate(dto.getBirthDate())
            .weddingDate(dto.getWeddingDate())
            .oauthProvider("local")
            .oauthId(UUID.randomUUID().toString())
            .role(UserRole.USER)
            .status(Status.ACTIVE)
            .build();
    }

}
