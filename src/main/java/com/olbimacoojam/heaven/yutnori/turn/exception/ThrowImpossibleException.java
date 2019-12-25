package com.olbimacoojam.heaven.yutnori.turn.exception;

public class ThrowImpossibleException extends RuntimeException {

    private static final String MESSAGE = "윷을 던질 수 없습니다. 말을 움직이세요.";

    public ThrowImpossibleException() {
        super(MESSAGE);
    }
}
