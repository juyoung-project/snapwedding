package kr.or.wds.project.common;

public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive"),
    SUSPENDED("suspended");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}