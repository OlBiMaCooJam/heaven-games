package com.olbimacoojam.heaven.yutnori.exception;

public class NotExistYutException extends RuntimeException {
    private static final String MESSAGE = " 존재하지 않습니다";

    public NotExistYutException(String name) {
        super(name + MESSAGE);
    }
}
