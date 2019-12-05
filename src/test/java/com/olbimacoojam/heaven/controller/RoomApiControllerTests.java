package com.olbimacoojam.heaven.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomApiControllerTests {
    public static final String ROOMS_URL = "/rooms";
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("게임방 생성테스트")
    @DirtiesContext
    void create_room_test() {
        webTestClient.post()
                .uri(ROOMS_URL)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader()
                .exists("location");
    }

    @Test
    void list_room_test() {
        int roomId1 = createRoom();
        int roomId2 = createRoom();

        webTestClient.get()
                .uri(ROOMS_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(roomId1)
                .jsonPath("$[1].id").isEqualTo(roomId2);

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
}
