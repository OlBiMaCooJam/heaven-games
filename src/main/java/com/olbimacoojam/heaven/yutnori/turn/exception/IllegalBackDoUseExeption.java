package com.olbimacoojam.heaven.yutnori.turn.exception;

public class IllegalBackDoUseExeption extends RuntimeException {
    private static final String MESSAGE = "지정한 말은 빽도로 움직이지 못합니다. 현재 길에 나와 있는 말들 중 빽도를 사용해 주세요";

    public IllegalBackDoUseExeption() {
        super(MESSAGE);
    }
}
