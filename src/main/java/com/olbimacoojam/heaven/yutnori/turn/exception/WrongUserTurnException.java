package com.olbimacoojam.heaven.yutnori.turn.exception;

public class WrongUserTurnException extends RuntimeException {

    private static final String MESSAGE = "%s의 턴 입니다. %s의 턴을 기다리세요.";

    public WrongUserTurnException(String turnUser, String wrongTurnUser) {
        super(String.format(MESSAGE, turnUser, wrongTurnUser));
    }
}
