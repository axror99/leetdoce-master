package com.example.leetdoce.simple_class;

public enum LevelQuestion {
    HARD("hard"),
    MEDIUM("medium"),
    EASY("easy");
    private final String level;

    LevelQuestion(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
