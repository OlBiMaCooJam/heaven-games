package com.olbimacoojam.heaven.yutnori.turn.exception;

public class MoveImpossibleException extends RuntimeException {

    private static final String MESSAGE = "말을 움직일 수 없습니다. 윷을 던지세요.";

    public MoveImpossibleException() {
        super(MESSAGE);
    }
}
