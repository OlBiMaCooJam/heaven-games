package com.olbimacoojam.heaven.mafia;

public class NotFoundMafiaParticipantException extends RuntimeException {
    public NotFoundMafiaParticipantException() {
        super();
    }

    public NotFoundMafiaParticipantException(String message) {
        super(message);
    }
}
