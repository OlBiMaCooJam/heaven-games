package com.olbimacoojam.heaven.yutnori.participant.exception;

public class IllegalYutnoriParticipantsException extends RuntimeException {

    private static final String MESSAGE = "윷놀이는 2인용 게임입니다.";

    public IllegalYutnoriParticipantsException(int num) {
        super(num + "인 윷놀이 불가. " + MESSAGE);
    }
}
