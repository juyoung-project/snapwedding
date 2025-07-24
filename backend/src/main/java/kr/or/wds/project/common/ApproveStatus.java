package kr.or.wds.project.common;

public enum ApproveStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String value;

    ApproveStatus(String value) {
        this.value = value;
    }       

    public String getValue() {
        return value;
    }
}
