package com.olbimacoojam.heaven;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(properties = "spring.config.location=classpath:kakao.yml", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {
    private WebTestClient webTestClient;
    @LocalServerPort
    private String port;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void login() {
        webTestClient
                .get()
                .uri("/login/kakao")
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .consumeWith(document("login-api/login"));
    }
}