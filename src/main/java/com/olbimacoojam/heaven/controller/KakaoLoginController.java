package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.service.KakaoApiService;
import com.olbimacoojam.heaven.service.TokenInfo;
import com.olbimacoojam.heaven.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class KakaoLoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KakaoLoginController.class);

    private final UserService userService;
    private final KakaoApiService kakaoApiService;

    public KakaoLoginController(UserService userService, KakaoApiService kakaoApiService) {
        LOGGER.info("kakaologin");
        this.userService = userService;
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

        TokenInfo tokenInfo = kakaoApiService.getTokenInfo(code);
        LOGGER.info("tokenInfo: {}", tokenInfo);

        String accessToken = tokenInfo.getAccess_token();
        String refreshToken = tokenInfo.getRefresh_token();

        User user = userService.save(kakaoApiService.getUser(accessToken, refreshToken));
        LOGGER.info("user: {}", user);

        UserSession userSession = new UserSession(user.getId(), user.getName(), accessToken);
        httpSession.setAttribute(UserSession.USER_SESSION, userSession);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/");
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpSession httpSession) {
        httpSession.removeAttribute(UserSession.USER_SESSION);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/signout")
    public ResponseEntity signout(HttpSession httpSession) {
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);
        userService.deleteById(userSession.getId());

        httpSession.removeAttribute(UserSession.USER_SESSION);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/");
        return ResponseEntity.ok().build();
    }
}
