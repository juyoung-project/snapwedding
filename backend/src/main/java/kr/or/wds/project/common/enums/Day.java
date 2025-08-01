package kr.or.wds.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Day {
    MON("1"),
    TUE("2"),
    WED("3"),
    THU("4"),
    FRI("5"),
    SAT("6"),
    SUN("7");

    private final String value;

}
