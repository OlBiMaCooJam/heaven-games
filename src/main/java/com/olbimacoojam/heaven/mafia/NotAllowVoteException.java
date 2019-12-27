package com.olbimacoojam.heaven.mafia;

public class NotAllowVoteException extends RuntimeException {
    public NotAllowVoteException() {
        super();
    }

    public NotAllowVoteException(String message) {
        super(message);
    }
}
