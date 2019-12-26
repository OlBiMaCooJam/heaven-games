package com.olbimacoojam.heaven.mafia;

public class NotAllowChatException extends RuntimeException {
    public NotAllowChatException() {
        super();
    }

    public NotAllowChatException(String message) {
        super(message);
    }
}
