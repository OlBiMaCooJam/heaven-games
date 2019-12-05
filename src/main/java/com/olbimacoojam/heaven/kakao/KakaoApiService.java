package com.olbimacoojam.heaven.kakao;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.olbimacoojam.heaven.game.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KakaoApiService.class);
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private KakaoConfig kakaoConfig;

    public KakaoApiService(KakaoConfig kakaoConfig) {
        this.kakaoConfig = kakaoConfig;
    }

    public String getLoginRequestUri() {
        StringBuilder sb = new StringBuilder();
        sb.append(kakaoConfig.getAuth().get("host"));
        sb.append(kakaoConfig.getAuth().get("authorizePath"));
        sb.append("?client_id=" + kakaoConfig.getAuth().get("clientId"));
        sb.append("&redirect_uri=" + kakaoConfig.getAuth().get("redirectUri"));
        sb.append("&response_type=code");

        return sb.toString();
    }

    public String getAccessToken(String code) {
        return getToken(code, ACCESS_TOKEN);
    }

    public String getRefreshToken(String code) {
        return getToken(code, REFRESH_TOKEN);
    }

    private String getToken(String code, String tokenType) {
        String body = WebClient.create(kakaoConfig.getAuth().get("host"))
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(kakaoConfig.getAuth().get("tokenPath"))
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", kakaoConfig.getAuth().get("clientId"))
                        .queryParam("redirect_uri", kakaoConfig.getAuth().get("redirectUri"))
                        .queryParam("code", code).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return new JsonParser().parse(body).getAsJsonObject().get(tokenType).getAsString();
    }


    public User getUser(String accessToken) {
        String body = WebClient.create(kakaoConfig.getResource().get("host"))
                .post()
                .uri(kakaoConfig.getResource().get("userInfoPath"))
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Long kakaoId = Long.parseLong(new JsonParser().parse(body).getAsJsonObject().get("id").getAsString());

        JsonObject properties = new JsonParser().parse(body).getAsJsonObject().get("properties").getAsJsonObject();
        String name = properties.getAsJsonObject().get("nickname").getAsString();

        return new User(kakaoId, name);
    }
}
