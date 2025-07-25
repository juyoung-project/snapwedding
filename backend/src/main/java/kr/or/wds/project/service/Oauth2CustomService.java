package kr.or.wds.project.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.or.wds.project.common.Oauth2Provider;
import kr.or.wds.project.common.UserRole;
import kr.or.wds.project.common.UserStatus;
import kr.or.wds.project.entity.UserEntity;
import kr.or.wds.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2CustomService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = service.loadUser(userRequest); // OAuth2 정보를 가져옵니다.

		Map<String, Object> originAttributes = oAuth2User.getAttributes(); // OAuth2User의 attribute
		System.out.println("1번 먼저 작동 :: > " + originAttributes);

		String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인 원천지 (google, kakao, naver)
		UserEntity member = this.extractMember(registrationId, originAttributes);
		this.saveOrUpdate(member);

		Map<String, Object> customAttributes = this.setCustomAttributes(originAttributes, member);
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(UserRole.USER.getValue())),
				customAttributes,
				"email");
	}

	private UserEntity extractMember(String registrationId, Map<String, Object> attributes) {
        return Oauth2Provider.getOauthInfo(registrationId, attributes);
    }

	private Map<String, Object> setCustomAttributes(Map<String, Object> originAttributes, UserEntity member) {
		Map<String, Object> customAttributes = new HashMap<>(originAttributes);
		customAttributes.put("email", member.getEmail());
		return customAttributes;
	}


	private void saveOrUpdate(UserEntity memberEntity) {
        userRepository.findByEmail(memberEntity.getEmail())
            .ifPresentOrElse(
                existing -> {
                    // 필요한 경우 업데이트 로직 추가
                },
                () -> {
                    UserEntity newMember = UserEntity.builder()
                        .email(memberEntity.getEmail())
                        .name(memberEntity.getName())
						.oauthProvider(memberEntity.getOauthProvider())
						.oauthId(memberEntity.getOauthId())
                        .password(UUID.randomUUID().toString()) // 임시 비밀번호
						.role(UserRole.USER)
                        .status(UserStatus.ACTIVE)
                        .build();
                    userRepository.save(newMember);
                }
            );
    }

}
