package kr.or.wds.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String value;


}