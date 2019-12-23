package com.olbimacoojam.heaven.yutnori.exception;

public class NotExistParticipantException extends RuntimeException {
    private static final String MESSAGE = "은 게임에 참여하고 있지 않습니다.";

    public NotExistParticipantException(String name) {
        super(name + MESSAGE);
    }
}
