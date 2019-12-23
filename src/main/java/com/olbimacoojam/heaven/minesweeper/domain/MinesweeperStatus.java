package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.Getter;

@Getter
public enum MinesweeperStatus {
    WIN,
    LOSE,
    PLAYING;

    public boolean isGameOver() {
        return LOSE.equals(this);
    }
}
