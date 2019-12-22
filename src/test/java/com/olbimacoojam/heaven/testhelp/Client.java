package com.olbimacoojam.heaven.testhelp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;

@Getter
@RequiredArgsConstructor
public class Client {
    private final StompSession stompSession;
    private final String jessionId;
}
