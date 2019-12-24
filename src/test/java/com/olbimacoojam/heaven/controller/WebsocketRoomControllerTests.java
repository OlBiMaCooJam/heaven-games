package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.testhelp.Client;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebsocketRoomControllerTests {
    private static final String JSESSIONID = "JSESSIONID";
    public static final String ROOMS_URL = "/rooms";
    private static final String SEND_ENTER_ROOM_ENDPOINT = "/app/rooms/";
    private static final String SUBSCRIBE_ENTER_ROOM_ENDPOINT = "/topic/rooms/";

    @Autowired
    private WebTestClient webTestClient;
    @LocalServerPort
    private String port;
    private String URL;
    private CompletableFuture<RoomResponseDto> completableFuture;
    private WebSocketStompClient stompClient;

    @BeforeEach
    void setup() {
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/websocket";

        stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

//    @Test
//    void enter_room_test() throws Exception {
//        Client client = createClient(9L);
//        int roomId = createRoom();
//        client.getStompSession().subscribe(SUBSCRIBE_ENTER_ROOM_ENDPOINT + roomId, getStompFrameHandler(completableFuture));
//        client.getStompSession().send(SEND_ENTER_ROOM_ENDPOINT + roomId, null);
//
//        RoomResponseDto roomResponseDto = completableFuture.get(1, SECONDS);
//        assertThat(roomResponseDto.getId()).isEqualTo(roomId);
//        assertThat(roomResponseDto.getPlayers()).hasSize(1);
//    }

//    @Test
//    void leave_room_test() throws InterruptedException, ExecutionException, TimeoutException {
//        Client client = createClient(10L);
//        int roomId = createRoom();
//
//        client.getStompSession().subscribe(SUBSCRIBE_ENTER_ROOM_ENDPOINT + roomId, getStompFrameHandler(completableFuture));
//        client.getStompSession().send(SEND_ENTER_ROOM_ENDPOINT + roomId, null);
//
//        RoomResponseDto roomResponseDto = completableFuture.get(1, SECONDS);
//        assertThat(roomResponseDto.getId()).isEqualTo(roomId);
//        assertThat(roomResponseDto.getPlayers()).hasSize(1);
//
//        CompletableFuture<RoomResponseDto> completableFuture2 = new CompletableFuture<>();
//        client.getStompSession().subscribe(SUBSCRIBE_ENTER_ROOM_ENDPOINT + roomId, getStompFrameHandler(completableFuture2));
//        client.getStompSession().send(SEND_ENTER_ROOM_ENDPOINT + roomId + "/leave", null);
//        RoomResponseDto roomResponseDto2 = completableFuture2.get(10, SECONDS);
//        assertThat(roomResponseDto2.getId()).isEqualTo(roomId);
//        assertThat(roomResponseDto2.getPlayers()).hasSize(0);
//        Thread.sleep(1000);
//    }

    private Client createClient(Long userId) throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String mockJsessionId = mockLogin(userId);

        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.set(HttpHeaders.COOKIE, JSESSIONID + " = " + mockJsessionId);

        StompSession stompSession = webSocketStompClient.connect("ws://localhost:" + port + "/websocket", webSocketHttpHeaders, new StompSessionHandlerAdapter() {
        }).get(5, TimeUnit.SECONDS);

        return new Client(stompSession, mockJsessionId);
    }

    private String mockLogin(Long userId) {
        return webTestClient.post()
                .uri("/mock/login")
                .body(Mono.just(userId), Long.class)
                .exchange()
                .returnResult(String.class)
                .getResponseCookies()
                .get(JSESSIONID).get(0).getValue();
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private int createRoom() {
        String location = webTestClient.post()
                .uri(ROOMS_URL)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseHeaders()
                .get("location").get(0);
        return Integer.parseInt(location.substring(ROOMS_URL.length()));
    }

    private StompFrameHandler getStompFrameHandler(CompletableFuture<RoomResponseDto> completableFuture) {
        return new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return RoomResponseDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((RoomResponseDto) payload);
            }
        };
    }
}
