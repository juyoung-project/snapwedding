package kr.or.wds.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    UNPAID("unpaid"),
    PAID("paid"),
    REFUNDED("refunded");

    private final String value;
}
