package com.olbimacoojam.heaven.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("게임방 생성테스트")
    void create_room_test() {
        webTestClient.post()
                .uri("/room")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader()
                .exists("location");
    }
}
