package com.olbimacoojam.heaven.domain.exception;

public class GamePlayingException extends RuntimeException {

    private static final String MESSAGE = "게임이 진행 중입니다.";

    public GamePlayingException() {
        super(MESSAGE);
    }
}
