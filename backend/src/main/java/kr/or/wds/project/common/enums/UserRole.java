package kr.or.wds.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String value;


}
