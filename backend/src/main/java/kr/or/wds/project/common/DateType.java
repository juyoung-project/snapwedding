package kr.or.wds.project.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateType {
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");
    private final String value;

}


