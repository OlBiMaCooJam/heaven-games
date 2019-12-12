package com.olbimacoojam.heaven.minesweeper.domain.exception;

public class InvalidNumberOfUsersException extends RuntimeException {

    public InvalidNumberOfUsersException(Integer expected, Integer actual) {
        super(buildMessage("잘못된 플레이어 수 입니다. expected : %d, actual : %d", expected, actual));
    }

    private static String buildMessage(String message, Integer expected, Integer actual) {
        return String.format(message, expected, actual);
    }
}
