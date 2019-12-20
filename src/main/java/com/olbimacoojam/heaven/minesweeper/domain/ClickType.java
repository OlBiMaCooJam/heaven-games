package com.olbimacoojam.heaven.minesweeper.domain;

public enum ClickType {
    LEFT(true),
    RIGHT(false);

    private final boolean isLeftClick;

    ClickType(boolean isLeftClick) {
        this.isLeftClick = isLeftClick;
    }

    public boolean isLeftClick() {
        return isLeftClick;
    }
}
