package com.olbimacoojam.heaven.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:kakao.yml")
@ConfigurationProperties(prefix = "kakao")
@Getter
@Setter
public class KakaoConfig {
    private Map<String, String> auth;
    private Map<String, String> resource;

    public KakaoConfig() {
        this.auth = new HashMap<>();
        this.resource = new HashMap<>();
    }
}
