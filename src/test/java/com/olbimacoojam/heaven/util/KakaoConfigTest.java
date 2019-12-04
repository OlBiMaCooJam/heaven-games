package com.olbimacoojam.heaven.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.config.location=classpath:kakao.yml")
class KakaoConfigTest {
    @Autowired
    private KakaoConfig kakaoConfig;

    @Test
    void KakaoConfig_yml파일_매핑테스트_client() {
        assertThat(kakaoConfig.getClient().get("clientId")).isNotNull();
    }

    @Test
    void KakaoConfig_yml파일_매핑테스트_resource() {
        assertThat(kakaoConfig.getResource().get("userInfoUri")).isNotNull();
    }
}