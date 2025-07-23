package kr.or.wds.project.common;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import kr.or.wds.project.entity.UserEntity;

public enum Oauth2Provider {
	LOCAL("local", (attributes) -> {
		Map<String, Object> jsonMap = (Map<String, Object>) attributes.get("response");
		return UserEntity.builder()
				.name(String.valueOf(jsonMap.get("name")))
				.oauthId(String.valueOf(jsonMap.get("id")))
				.oauthProvider("local")
				.email(String.valueOf(jsonMap.get("email"))).build();
	}),
	NAVER("naver", (attributes) -> {
		Map<String, Object> jsonMap = (Map<String, Object>) attributes.get("response");
		return UserEntity.builder()
				.name(String.valueOf(jsonMap.get("name")))
				.oauthId(String.valueOf(jsonMap.get("id")))
				.oauthProvider("naver") 
				.email(String.valueOf(jsonMap.get("email"))).build();
	});

	private final String registrationId;
	private final Function<Map<String, Object>, UserEntity> of;

	Oauth2Provider(String registrationId, Function<Map<String, Object>, UserEntity> of) {
		this.registrationId = registrationId;
		this.of = of;
	}
	
	public static UserEntity getOauthInfo(String registrationId, Map<String, Object> attributes) {
		return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
           .of.apply(attributes);
		
	}
}
