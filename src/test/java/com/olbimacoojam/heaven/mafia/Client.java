package com.olbimacoojam.heaven.mafia;

import lombok.Getter;
import org.springframework.messaging.simp.stomp.StompSession;

@Getter
public class Client {
    private final StompSession stompSession;
    private final String jsessionId;

    public Client(StompSession stompSession, String jsessionId) {
        this.stompSession = stompSession;
        this.jsessionId = jsessionId;
    }
}
