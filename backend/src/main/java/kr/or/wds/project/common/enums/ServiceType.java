package kr.or.wds.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {
    SNAP_EXPERT("snap_expert"),
    MC_EXPERT("mc_expert"),
    SINGER_EXPERT("singer_expert"),
    PHOTOGRAPHER_EXPERT("photographer_expert");

    private final String value;

}
