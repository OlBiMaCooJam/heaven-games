package com.olbimacoojam.heaven;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(properties = "spring.config.location=classpath:kakao.yml", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void login() {
        webTestClient
                .get()
                .uri("/login")
                .exchange()
                .expectStatus().isFound();
    }
}