package com.olbimacoojam.heaven.controller.yutnori;

import com.olbimacoojam.heaven.dto.GameStartResponseDtos;
import com.olbimacoojam.heaven.dto.MoveRequestDto;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.dto.YutResponse;
import com.olbimacoojam.heaven.testhelp.Client;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YutnoriGameControllerTest {
    private static final String JSESSIONID = "JSESSIONID";
    private static final String ROOMS_URL = "/rooms";
    private static final String SUBSCRIBE_ROOM_ENDPOINT = "/topic/rooms/";
    private static final String SEND_ROOM_ENDPOINT = "/app/rooms/";


    @LocalServerPort
    private Long port;

    @Autowired
    private WebTestClient webTestClient;
    private List<Client> clients;
    private List<RoomResponseDto> list;
    private Client firstClient;
    private Client secondClient;


    @Test
    @DisplayName("게임시작요청 Test")
    void start_game() throws InterruptedException, ExecutionException, TimeoutException {

        //두 명의 클라이언트 로그인
        list = new ArrayList<>();
        Client firstClient = createClient();
        Client secondClient = createClient();

        //한 클라이언트가 방을 만든다
        int roomId = createRoom();

        // 두명의 클라이언트가 방에 입장
        CompletableFuture<RoomResponseDto> completableFutureForFirstClient = new CompletableFuture<>();
        CompletableFuture<RoomResponseDto> completableFutureForSecondClient = new CompletableFuture<>();
        firstClient.getStompSession().subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId, getStompFrameHandlerRoomResponseDto(completableFutureForFirstClient, list));
        secondClient.getStompSession().subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId, getStompFrameHandlerRoomResponseDto(completableFutureForSecondClient, list));

        firstClient.getStompSession().send(SEND_ROOM_ENDPOINT + roomId, null);
        secondClient.getStompSession().send(SEND_ROOM_ENDPOINT + roomId, null);
        RoomResponseDto roomResponseDto = completableFutureForSecondClient.get(200, SECONDS);

        assertThat(roomResponseDto.getId()).isEqualTo(roomId);
        assertThat(roomResponseDto.getPlayers()).hasSize(2);


        //한 클라이언트가 게임을 시작함
        CompletableFuture<GameStartResponseDtos> completableFutureForFirstClientGameStartResponseDtos = new CompletableFuture<>();
        CompletableFuture<GameStartResponseDtos> completableFutureForSecondClientGameStartResponseDtos = new CompletableFuture<>();
        firstClient.getStompSession().subscribe("/topic/yutnori/" + roomId, getStompFrameHandlerGameStartResponse(completableFutureForFirstClientGameStartResponseDtos));
        secondClient.getStompSession().subscribe("/topic/yutnori/" + roomId, getStompFrameHandlerGameStartResponse(completableFutureForSecondClientGameStartResponseDtos));

        firstClient.getStompSession().send("/app/yutnori/" + roomId, null);

        GameStartResponseDtos gameStartResponseDtos = completableFutureForFirstClientGameStartResponseDtos.get(100, SECONDS);
        assertThat(gameStartResponseDtos.size()).isEqualTo(2);
        assertThat(gameStartResponseDtos.containsUser("mockUser0")).isTrue();
        assertThat(gameStartResponseDtos.containsUser("mockUser1")).isTrue();
    }

    @Test
    @DisplayName("윷던지기요청 Test")
    void throw_yut() throws InterruptedException, ExecutionException, TimeoutException {

        //두 명의 클라이언트 로그인
        list = new ArrayList<>();
        Client firstClient = createClient();
        Client secondClient = createClient();

        //한 클라이언트가 방을 만든다
        int roomId = createRoom();

        // 두명의 클라이언트가 방에 입장
        CompletableFuture<RoomResponseDto> completableFutureForFirstClient = new CompletableFuture<>();
        CompletableFuture<RoomResponseDto> completableFutureForSecondClient = new CompletableFuture<>();
        firstClient.getStompSession().subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId, getStompFrameHandlerRoomResponseDto(completableFutureForFirstClient, list));
        secondClient.getStompSession().subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId, getStompFrameHandlerRoomResponseDto(completableFutureForSecondClient, list));
        firstClient.getStompSession().send(SEND_ROOM_ENDPOINT + roomId, null);
        secondClient.getStompSession().send(SEND_ROOM_ENDPOINT + roomId, null);

        RoomResponseDto roomResponseDto = completableFutureForFirstClient.get(200, SECONDS);
        assertThat(roomResponseDto.getId()).isEqualTo(roomId);
        assertThat(roomResponseDto.getPlayers()).hasSize(2);


        //한 클라이언트가 게임을 시작함
        CompletableFuture<GameStartResponseDtos> completableFutureForFirstClientGameStartResponseDtos = new CompletableFuture<>();
        CompletableFuture<GameStartResponseDtos> completableFutureForSecondClientGameStartResponseDtos = new CompletableFuture<>();
        firstClient.getStompSession().subscribe("/topic/yutnori/" + roomId, getStompFrameHandlerGameStartResponse(completableFutureForFirstClientGameStartResponseDtos));
        secondClient.getStompSession().subscribe("/topic/yutnori/" + roomId, getStompFrameHandlerGameStartResponse(completableFutureForSecondClientGameStartResponseDtos));

        firstClient.getStompSession().send("/app/yutnori/" + roomId, null);

        GameStartResponseDtos gameStartResponseDtos = completableFutureForFirstClientGameStartResponseDtos.get(100, SECONDS);
        assertThat(gameStartResponseDtos.size()).isEqualTo(2);
        assertThat(gameStartResponseDtos.containsUser("mockUser0")).isTrue();
        assertThat(gameStartResponseDtos.containsUser("mockUser1")).isTrue();

        //윷던지기
        CompletableFuture<YutResponse> completableFutureForFirstClientYutResponse = new CompletableFuture<>();
        CompletableFuture<YutResponse> completableFutureForSecondClientYutResponse = new CompletableFuture<>();
        firstClient.getStompSession().subscribe("/topic/yutnori/" + roomId + "/yut-throw", getStompFrameHandlerYutResponse(completableFutureForFirstClientYutResponse));
        secondClient.getStompSession().subscribe("/topic/yutnori" + roomId + "/yut-throw", getStompFrameHandlerYutResponse(completableFutureForSecondClientYutResponse));

        firstClient.getStompSession().send("/app/yutnori/" + roomId + "/yut-throw", null);

        YutResponse yutResponse = completableFutureForFirstClientYutResponse.get(100, SECONDS);
        List<String> yutNames = Arrays.stream(Yut.values())
                .map(yut -> yut.name())
                .collect(Collectors.toList());
        assertThat(yutNames).contains(yutResponse.getYut());
    }

    @Test
    @DisplayName("윷던지기요청 Test")
    void move_piece() throws InterruptedException, ExecutionException, TimeoutException {

        //두 명의 클라이언트 로그인
        list = new ArrayList<>();
        Client firstClient = createClient();
        Client secondClient = createClient();

        //한 클라이언트가 방을 만든다
        int roomId = createRoom();

        // 두명의 클라이언트가 방에 입장
        CompletableFuture<RoomResponseDto> completableFutureForFirstClient = new CompletableFuture<>();
        CompletableFuture<RoomResponseDto> completableFutureForSecondClient = new CompletableFuture<>();
        firstClient.getStompSession().subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId, getStompFrameHandlerRoomResponseDto(completableFutureForFirstClient, list));
        secondClient.getStompSession().subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId, getStompFrameHandlerRoomResponseDto(completableFutureForSecondClient, list));
        firstClient.getStompSession().send(SEND_ROOM_ENDPOINT + roomId, null);
        secondClient.getStompSession().send(SEND_ROOM_ENDPOINT + roomId, null);

        RoomResponseDto roomResponseDto = completableFutureForFirstClient.get(200, SECONDS);
        assertThat(roomResponseDto.getId()).isEqualTo(roomId);
        assertThat(roomResponseDto.getPlayers()).hasSize(2);


        //한 클라이언트가 게임을 시작함
        CompletableFuture<GameStartResponseDtos> completableFutureForFirstClientGameStartResponseDtos = new CompletableFuture<>();
        CompletableFuture<GameStartResponseDtos> completableFutureForSecondClientGameStartResponseDtos = new CompletableFuture<>();
        firstClient.getStompSession().subscribe("/topic/yutnori/" + roomId, getStompFrameHandlerGameStartResponse(completableFutureForFirstClientGameStartResponseDtos));
        secondClient.getStompSession().subscribe("/topic/yutnori/" + roomId, getStompFrameHandlerGameStartResponse(completableFutureForSecondClientGameStartResponseDtos));

        firstClient.getStompSession().send("/app/yutnori/" + roomId, null);

        GameStartResponseDtos gameStartResponseDtos = completableFutureForFirstClientGameStartResponseDtos.get(100, SECONDS);
        assertThat(gameStartResponseDtos.size()).isEqualTo(2);
        assertThat(gameStartResponseDtos.containsUser("mockUser0")).isTrue();
        assertThat(gameStartResponseDtos.containsUser("mockUser1")).isTrue();

        //윷던지기
        CompletableFuture<YutResponse> completableFutureForFirstClientYutResponse = new CompletableFuture<>();
        CompletableFuture<YutResponse> completableFutureForSecondClientYutResponse = new CompletableFuture<>();
        firstClient.getStompSession().subscribe("/topic/yutnori/" + roomId + "/yut-throw", getStompFrameHandlerYutResponse(completableFutureForFirstClientYutResponse));
        secondClient.getStompSession().subscribe("/topic/yutnori" + roomId + "/yut-throw", getStompFrameHandlerYutResponse(completableFutureForSecondClientYutResponse));

        firstClient.getStompSession().send("/app/yutnori/" + roomId + "/yut-throw", null);

        YutResponse yutResponse = completableFutureForFirstClientYutResponse.get(100, SECONDS);

        //말 움직이기
        CompletableFuture<MoveResults> completableFutureForFirstClientMoveResults = new CompletableFuture<>();
        CompletableFuture<MoveResults> completableFutureForFSecondClientMoveResults = new CompletableFuture<>();

        firstClient.getStompSession().subscribe("/topic/yutnori/" + roomId, getStompFramHandlerMoveResults(completableFutureForFirstClientMoveResults));
        secondClient.getStompSession().subscribe("/topic/yutnori/" + roomId, getStompFramHandlerMoveResults(completableFutureForFSecondClientMoveResults));

        MoveRequestDto moveRequestDto = new MoveRequestDto("STANDBY", "DO");
        firstClient.getStompSession().send("/app/yutnori/" + roomId + "/move-piece", moveRequestDto);

        MoveResults moveResults = completableFutureForFirstClientMoveResults.get(100, SECONDS);
    }

    private StompFrameHandler getStompFramHandlerMoveResults(CompletableFuture<MoveResults> completableFutureForFirstClientMoveResults) {
        return new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return MoveResults.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFutureForFirstClientMoveResults.complete((MoveResults) payload);
            }
        };
    }

    private StompFrameHandler getStompFrameHandlerYutResponse(CompletableFuture<YutResponse> completableFutureForFirstClientYutResponse) {
        return new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return YutResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFutureForFirstClientYutResponse.complete((YutResponse) payload);
            }
        };
    }

    private StompFrameHandler getStompFrameHandlerGameStartResponse(CompletableFuture<GameStartResponseDtos> completableFutureForGameStartResponseDtos) {
        return new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return GameStartResponseDtos.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFutureForGameStartResponseDtos.complete((GameStartResponseDtos) payload);
            }
        };
    }

    private StompFrameHandler getStompFrameHandlerRoomResponseDto(CompletableFuture<RoomResponseDto> completableFuture, List<RoomResponseDto> list) {
        return new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return RoomResponseDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                list.add((RoomResponseDto) payload);
                completableFuture.complete((RoomResponseDto) payload);
            }
        };
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

    private Client createClient() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String mockJsessionId = mockLogin();

        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.set(HttpHeaders.COOKIE, JSESSIONID + " = " + mockJsessionId);

        StompSession stompSession = webSocketStompClient.connect("ws://localhost:" + port + "/websocket", webSocketHttpHeaders, new StompSessionHandlerAdapter() {
        }).get(5, TimeUnit.SECONDS);

        return new Client(stompSession, mockJsessionId);
    }

    private String mockLogin() {
        return webTestClient.get()
                .uri("/mock/login")
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
}