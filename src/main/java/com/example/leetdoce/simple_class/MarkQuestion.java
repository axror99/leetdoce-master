package com.example.leetdoce.simple_class;

public enum MarkQuestion {
    SUCCESS(1, "yes"),
    TRIED(2, "tried"),
    NOT_CHANGED(0, "no");

    private final int code;
    private final String description;

    MarkQuestion(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
