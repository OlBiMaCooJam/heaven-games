package com.olbimacoojam.heaven.service;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("찾을 수 없는 사용자 입니다.");
    }
}
