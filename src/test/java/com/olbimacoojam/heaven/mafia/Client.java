package com.olbimacoojam.heaven.mafia;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;

@Getter
@RequiredArgsConstructor
public class Client {
    private final StompSession stompSession;
    private final String jsessionId;
}
