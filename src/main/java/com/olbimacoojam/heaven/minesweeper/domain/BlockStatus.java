package com.olbimacoojam.heaven.minesweeper.domain;

public enum BlockStatus {
    CLICKED,
    MINE,
    UNCLICKED,
    FLAG,
    QUESTION_MARK;

    public BlockStatus nextStatus(ClickType clickType, boolean isMine) {
        if (clickType.isLeftClick()) {
            return isMine ? MINE : CLICKED;
        }

        if (CLICKED.equals(this) || MINE.equals(this)) {
            return this;
        }

        return nextRightClickStatus();
    }

    private BlockStatus nextRightClickStatus() {
        if (UNCLICKED.equals(this)) {
            return FLAG;
        }

        if (FLAG.equals(this)) {
            return QUESTION_MARK;
        }

        return UNCLICKED;
    }

    public boolean isClicked() {
        return CLICKED.equals(this);
    }

    public boolean isMine() {
        return MINE.equals(this);
    }
}
