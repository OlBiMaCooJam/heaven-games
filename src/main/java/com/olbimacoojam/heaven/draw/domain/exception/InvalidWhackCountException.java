package com.olbimacoojam.heaven.draw.domain.exception;

public class InvalidWhackCountException extends RuntimeException {

    public InvalidWhackCountException(int person, int whack) {
        super(String.format("폭탄 개수는 사람 수보다 작아야합니다. 사람 수 : %d, 폭탄 수 : %d", person, whack));
    }
}
