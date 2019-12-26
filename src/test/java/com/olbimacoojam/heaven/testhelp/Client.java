package com.olbimacoojam.heaven.testhelp;

import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.dto.YutnoriStateResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@Getter
@RequiredArgsConstructor
public class Client {

    private final Long kakaoId;
    private final StompSession stompSession;
    private final WebSocketStompClient webSocketStompClient;

    private CompletableFuture completableFuture;

    public Client(Long kakaoId, WebSocketStompClient webSocketStompClient, StompSession stompSession) {
        this.kakaoId = kakaoId;
        this.webSocketStompClient = webSocketStompClient;
        this.stompSession = stompSession;
    }

    public void disconnect() {
        if (webSocketStompClient != null) webSocketStompClient.stop();
        if (stompSession != null) stompSession.disconnect();
    }

    public <T> void subscribe(String destination, Class<T> clazz) {
        completableFuture = new CompletableFuture<T>();
        stompSession.subscribe(destination, getStompFrameHandler(completableFuture, clazz));
    }

    private <T> StompFrameHandler getStompFrameHandler(CompletableFuture<T> completableFuture, Class<T> clazz) {
        return new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return clazz;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((T) payload);
            }
        };
    }

    public void send(String destination, Object payload) {
        stompSession.send(destination, payload);
    }

    public <T> T getFromCompletableFuture() throws InterruptedException, ExecutionException, TimeoutException {
        return (T) completableFuture.get(10, SECONDS);
    }
}