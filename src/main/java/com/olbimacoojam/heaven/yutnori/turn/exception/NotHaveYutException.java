package com.olbimacoojam.heaven.yutnori.turn.exception;

import com.olbimacoojam.heaven.yutnori.yut.Yut;

public class NotHaveYutException extends RuntimeException {

    private static final String MESSAGE = "은 던지지 않았습니다.";

    public NotHaveYutException(Yut yut) {
        super(yut + MESSAGE);
    }
}
