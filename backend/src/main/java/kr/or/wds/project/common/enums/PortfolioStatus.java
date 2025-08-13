package kr.or.wds.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PortfolioStatus {
    DRAFT("draft"), // 임시 저장
    PUBLISHED("published"), // 공개
    HIDDEN("hidden"); // 비공개

    private final String value; 

}
