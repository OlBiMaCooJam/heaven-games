package com.olbimacoojam.heaven.minesweeper.domain.exception;

public class PositionOutOfBoardException extends RuntimeException {
    public PositionOutOfBoardException() {
        super("Position의 범위가 Board를 벗어났습니다.");
    }
}
