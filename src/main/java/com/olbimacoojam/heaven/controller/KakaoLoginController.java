package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.service.KakaoApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class KakaoLoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KakaoLoginController.class);

    private final KakaoApiService kakaoApiService;

    public KakaoLoginController(final KakaoApiService kakaoApiService) {
        this.kakaoApiService = kakaoApiService;
    }

    @GetMapping("/login/kakao")
    public void loginKakao(HttpServletResponse httpServletResponse) {
        String loginRequestUri = kakaoApiService.getLoginRequestUri();

        LOGGER.info("loginRequestUri: {}", loginRequestUri);

        httpServletResponse.setHeader("Location", loginRequestUri);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/oauth")
    public ResponseEntity oauth(HttpSession httpSession, @RequestParam("code") String code) {
        LOGGER.info("code: {}", code);

        String accessToken = kakaoApiService.getAccessToken(code);
        LOGGER.info("accessToken: {}", accessToken);

        String refreshToken = kakaoApiService.getRefreshToken(code);
        LOGGER.info("refreshToken: {}", refreshToken);

        User user = kakaoApiService.getUser(accessToken, refreshToken);
        LOGGER.info("user: {}", user);

        UserSession userSession = new UserSession(user.getId(), user.getName(), accessToken);
        httpSession.setAttribute(UserSession.USER_SESSION, userSession);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/");
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }
}
