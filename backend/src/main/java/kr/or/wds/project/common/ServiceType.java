package kr.or.wds.project.common;

public enum ServiceType {
    SNAP_EXPERT("snap_expert"),
    MC_EXPERT("mc_expert"),
    SINGER_EXPERT("singer_expert"),
    PHOTOGRAPHER_EXPERT("photographer_expert");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
