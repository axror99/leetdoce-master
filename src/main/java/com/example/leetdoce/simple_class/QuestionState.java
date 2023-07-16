package com.example.leetdoce.simple_class;

public enum QuestionState {

    SUCCESS(1),
    FAIL(0);
    private final int state;

    QuestionState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
