package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.mafia.Occupation.Occupation;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MafiaGameControllerTest {
    private static final String JSESSIONID = "JSESSIONID";
    private static final String ROOMS_URL = "/rooms";
    private static final String SEND_ROOM_ENDPOINT = "/app/rooms/";
    private static final String SUBSCRIBE_ROOM_ENDPOINT = "/topic/rooms/";

    @LocalServerPort
    private Long port;

    @Autowired
    private WebTestClient webTestClient;

    private List<Client> clients;

    @BeforeEach
    void setUp() throws InterruptedException, ExecutionException, TimeoutException {
        clients = new ArrayList<>();
        ;
        for (int i = 0; i < 4; i++) {
            clients.add(createClient());
        }
    }

    @Test
    void notifyOccupation() throws Exception {
        Long roomId = createRoom();

        List<CompletableFuture<RoomResponseDto>> completableFutures = new ArrayList<>();
        for (Client client : clients) {
            completableFutures.add(enterRoom(client, roomId));
        }

        for (CompletableFuture<RoomResponseDto> completableFuture : completableFutures) {
            completableFuture.get(5, SECONDS);
        }

        List<Occupation> occupations = new ArrayList<>();
        List<CompletableFuture<RoomResponseDto>> completableFutures2 = new ArrayList<>();

        for (Client client : clients) {
            completableFutures2.add(subscribeGameStart(client, roomId));
        }

        clients.get(0).getStompSession().send("/app/rooms/" + roomId + "/start", null);

        for (CompletableFuture<RoomResponseDto> completableFuture : completableFutures2) {
            completableFuture.get(5, SECONDS);
        }

        List<CompletableFuture<MafiaOccupationMessage>> completableFutures3 = new ArrayList<>();

        for (Client client : clients) {
            completableFutures3.add(getOccupation(client, roomId));
        }

        for (Client client : clients) {
            sendOccupation(client, roomId);
        }

        for (CompletableFuture<MafiaOccupationMessage> completableFuture : completableFutures3) {
            MafiaOccupationMessage mafiaOccupationMessage = completableFuture.get(50, SECONDS);
            occupations.add(mafiaOccupationMessage.getOccupation());
        }

        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < occupations.size(); i++) {
            if (occupations.get(i).equals(OccupationType.MAFIA)) {
                numberOfMafia++;
            }
            if (occupations.get(i).equals(OccupationType.DOCTOR)) {
                numberOfDoctor++;
            }
            if (occupations.get(i).equals(OccupationType.CITIZEN)) {
                numberOfCitizen++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(1);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(2);
    }

    private CompletableFuture<RoomResponseDto> subscribeGameStart(Client client, Long roomId) {
        StompSession stompSession = client.getStompSession();

        CompletableFuture<RoomResponseDto> completableFuture = new CompletableFuture<>();

        stompSession.subscribe("/topic/rooms/" + roomId + "/start",
                getRoomResponseDtoStompFrameHandler(completableFuture));

        return completableFuture;
    }

    private CompletableFuture<MafiaOccupationMessage> getOccupation(Client client, Long roomId) throws Exception {
        StompSession stompSession = client.getStompSession();

        CompletableFuture<MafiaOccupationMessage> completableFuture = new CompletableFuture<>();

        stompSession.subscribe("/queue/rooms/" + roomId + "/mafia/occupation",
                getOccupationStompFrameHandler(completableFuture));

        return completableFuture;
    }

    private void sendOccupation(Client client, Long roomId) {
        client.getStompSession().send("/app/rooms/" + roomId + "/mafia", null);
    }

    //TODO: stompClient.getSessionId ??
    private Client createClient() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String mockUserJsessionId = mockLogin();

        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.set(HttpHeaders.COOKIE, JSESSIONID + " = " + mockUserJsessionId);

        StompSession stompSession = webSocketStompClient.connect("ws://localhost:" + port + "/websocket", webSocketHttpHeaders, new StompSessionHandlerAdapter() {
        }).get(5, TimeUnit.SECONDS);

        return new Client(stompSession, mockUserJsessionId);
    }

    private CompletableFuture<RoomResponseDto> enterRoom(Client client, Long roomId) throws Exception {
        StompSession stompSession = client.getStompSession();
        CompletableFuture<RoomResponseDto> completableFuture = new CompletableFuture<>();
        stompSession.subscribe(SUBSCRIBE_ROOM_ENDPOINT + roomId, getRoomResponseDtoStompFrameHandler(completableFuture));
        stompSession.send(SEND_ROOM_ENDPOINT + roomId, null);
        return completableFuture;
    }

    private StompFrameHandler getRoomResponseDtoStompFrameHandler(CompletableFuture<RoomResponseDto> completableFuture) {
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

    private StompFrameHandler getOccupationStompFrameHandler(CompletableFuture<MafiaOccupationMessage> completableFuture) {
        return new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return MafiaOccupationMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((MafiaOccupationMessage) payload);
            }
        };
    }


    @Test
    void chat() {
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private String mockLogin() {
        return webTestClient.get()
                .uri("/mock/login")
                .exchange()
                .returnResult(String.class)
                .getResponseCookies()
                .get(JSESSIONID).get(0).getValue();
    }

    private Long createRoom() {
        String location = webTestClient.post()
                .uri(ROOMS_URL)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseHeaders()
                .get(HttpHeaders.LOCATION).get(0);
        return Long.parseLong(location.substring(ROOMS_URL.length()));
    }
}