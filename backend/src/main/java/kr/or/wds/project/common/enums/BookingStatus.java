package kr.or.wds.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookingStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    COMPLETED("completed"),
    CANCELLED("cancelled"),
    REJECTED("rejected");

    private final String value;
}