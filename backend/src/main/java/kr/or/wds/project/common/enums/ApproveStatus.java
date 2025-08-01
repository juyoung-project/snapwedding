package kr.or.wds.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApproveStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String value;

}
