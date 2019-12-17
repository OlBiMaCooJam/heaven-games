package com.olbimacoojam.heaven.minesweeper.domain.exception;

public class GameOverException extends RuntimeException {
    public GameOverException() {
        super("종료된 게임입니다.");
    }
}
