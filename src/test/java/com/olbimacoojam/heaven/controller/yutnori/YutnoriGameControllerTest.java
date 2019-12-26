package com.olbimacoojam.heaven.controller.yutnori;

import com.olbimacoojam.heaven.dto.*;
import com.olbimacoojam.heaven.game.GameKind2;
import com.olbimacoojam.heaven.testhelp.Client;
import com.olbimacoojam.heaven.yutnori.TestYutThrowConfiguration;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestYutThrowConfiguration.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YutnoriGameControllerTest {

    private static final String JSESSIONID = "JSESSIONID";
    private static final String ROOMS_URL = "/rooms";
    private static final String SUBSCRIBE_ROOM_ENDPOINT = "/topic/rooms/";
    private static final String SEND_ROOM_ENDPOINT = "/app/rooms/";
    private static final String SEND_ENTER_ROOM_ENDPOINT = "/app/rooms/";
    private static final String SUBSCRIBE_ENTER_ROOM_ENDPOINT = "/topic/rooms/";
    public static final String YUTNORI_SUBSCRIBE_DEST = "/topic/yutnori/";
    public static final String YUTNORI_SEND_DEST = "/app/yutnori/";

    @LocalServerPort
    private Long port;

    @Autowired
    private WebTestClient webTestClient;
    private Client firstClient;
    private Client secondClient;

    @Test
    @DisplayName("방 나가기 Test")
    void leave_room_test() throws InterruptedException, ExecutionException, TimeoutException {
        Client client = createClient(15L);
        int roomId = createRoom();
        System.err.println("roomId =>" + roomId);

        client.subscribe(SUBSCRIBE_ENTER_ROOM_ENDPOINT + roomId, RoomResponseDto.class);
        client.send(SEND_ENTER_ROOM_ENDPOINT + roomId, null);

        RoomResponseDto roomResponseDto = client.getFromCompletableFuture();
        assertThat(roomResponseDto.getId()).isEqualTo(roomId);
        assertThat(roomResponseDto.getPlayers()).hasSize(1);

        client.subscribe(SUBSCRIBE_ENTER_ROOM_ENDPOINT + roomId + "/leave", RoomResponseDto.class);
        client.send(SEND_ENTER_ROOM_ENDPOINT + roomId + "/leave", null);

        RoomResponseDto roomResponseDto2 = client.getFromCompletableFuture();
        assertThat(roomResponseDto2.getId()).isEqualTo(roomId);
        assertThat(roomResponseDto2.getPlayers()).hasSize(0);
    }

    @Test
    @DisplayName("게임시작요청 Test")
    void start_game() throws InterruptedException, ExecutionException, TimeoutException {
        startGame(1L, 2L);
    }

    @Test
    @DisplayName("윷던지기요청 Test")
    void throw_yut() throws InterruptedException, ExecutionException, TimeoutException {
        int roomId = startGame(3L, 4L);
        throwYut(roomId, true);
    }

    @Test
    @DisplayName("말 움직이기 Test")
    void move_piece() throws InterruptedException, ExecutionException, TimeoutException {
        int roomId = startGame(5L, 6L);
        throwYut(roomId, true);
        movePiece(roomId, true);
    }

    private int startGame(Long kakaoId1, Long kakaoId2) throws InterruptedException, ExecutionException, TimeoutException {
        //두 명의 클라이언트 로그인
        firstClient = createClient(kakaoId1);
        secondClient = createClient(kakaoId2);

        //한 클라이언트가 방을 만든다
        int roomId = createRoom();
        System.err.println("roomId =>" + roomId);

        // 두명의 클라이언트가 방에 입장
        enterRoom(firstClient, roomId);
        Thread.sleep(1000);
        enterRoom(secondClient, roomId);

        RoomResponseDto roomResponseDto = secondClient.getFromCompletableFuture();
        assertThat(roomResponseDto.getId()).isEqualTo(roomId);
        assertThat(roomResponseDto.getPlayers()).hasSize(2);

        //한 클라이언트가 게임을 시작함
        firstClient.subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId + "/start", GameStartResponseDto.class);
        firstClient.send(SEND_ROOM_ENDPOINT + roomId + "/start", null);

        GameStartResponseDto gameStartResponseDto = firstClient.getFromCompletableFuture();
        assertThat(gameStartResponseDto.isGameStart()).isTrue();
        assertThat(gameStartResponseDto.getNumberOfPlayers()).isEqualTo(2);
        return roomId;
    }

    private void enterRoom(Client client, int roomId) {
        client.subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId, RoomResponseDto.class);
        client.send(SEND_ROOM_ENDPOINT + roomId, null);
    }

    private void throwYut(int roomId, boolean isFirstClient) throws InterruptedException, ExecutionException, TimeoutException {
        Client thrower = isFirstClient ? firstClient : secondClient;

        thrower.subscribe(YUTNORI_SUBSCRIBE_DEST + roomId + "/yut-throw", YutResponse.class);
        thrower.send(YUTNORI_SEND_DEST + roomId + "/yut-throw", null);

        YutResponse yutResponse = thrower.getFromCompletableFuture();
        assertThat(Yut.values()).contains(yutResponse.getYut());
    }

    private void movePiece(int roomId, boolean isFirstClient) throws InterruptedException, ExecutionException, TimeoutException {
        Client mover = isFirstClient ? firstClient : secondClient;

        mover.subscribe(YUTNORI_SUBSCRIBE_DEST + roomId + "/playing", MoveResponse.class);
        mover.send(YUTNORI_SEND_DEST + roomId + "/move-piece", new MoveRequestDto(PointName.STANDBY, Yut.DO));

        MoveResponse moveResponse = mover.getFromCompletableFuture();
        assertThat(moveResponse.getFinish()).isFalse();
    }

    private int createRoom() {
        String location = webTestClient.post()
                .uri(ROOMS_URL + getGameKindQueryString(GameKind2.YUTNORI))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseHeaders()
                .get("location").get(0);
        return Integer.parseInt(location.substring(ROOMS_URL.length() + 1));
    }

    private String getGameKindQueryString(GameKind2 gameKind) {
        return String.format("?gameKind=%s", gameKind);
    }

    private Client createClient(Long kakaoId) throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String mockJsessionId = mockLogin(kakaoId);
        StompSession stompSession = getLoginedStompSession(webSocketStompClient, mockJsessionId);

        return new Client(kakaoId, webSocketStompClient, stompSession);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private String mockLogin(Long kakaoId) {
        return webTestClient.post()
                .uri("/mock/login")
                .body(Mono.just(kakaoId), Long.class)
                .exchange()
                .returnResult(String.class)
                .getResponseCookies()
                .get(JSESSIONID).get(0).getValue();
    }

    private StompSession getLoginedStompSession(WebSocketStompClient webSocketStompClient, String JSessionId) throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.set(HttpHeaders.COOKIE, JSESSIONID + " = " + JSessionId);

        return webSocketStompClient.connect("ws://localhost:" + port + "/websocket", webSocketHttpHeaders, new StompSessionHandlerAdapter() {
        }).get(5, TimeUnit.SECONDS);
    }

    @AfterEach
    void tearDown() {
        if (firstClient != null) {
            firstClient.disconnect();
        }
        if (secondClient != null) {
            secondClient.disconnect();
        }
    }
}


//
//
//    private void getYutnoriState(int roomId) throws InterruptedException, ExecutionException, TimeoutException {
//        Client player = firstClient;
//
//        player.subscribe("/topic/yutnori/" + roomId, YutnoriStateResponse.class);
//        player.send("/app/yutnori/" + roomId, null);
//
//        YutnoriStateResponse yutnoriStateResponse = player.getFromCompletableFuture();
//        System.out.println("----getYutnoriState----");
//        System.out.println(yutnoriStateResponse);
//    }