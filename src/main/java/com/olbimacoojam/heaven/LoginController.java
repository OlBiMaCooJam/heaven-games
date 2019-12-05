package com.olbimacoojam.heaven;

import com.olbimacoojam.heaven.game.User;
import com.olbimacoojam.heaven.kakao.KakaoApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private KakaoApiService kakaoApiService;

    public LoginController(KakaoApiService kakaoApiService) {
        this.kakaoApiService = kakaoApiService;
    }

    @GetMapping("/login")
    public void login(HttpServletResponse httpServletResponse) {
        String loginRequestUri = kakaoApiService.getLoginRequestUri();

        LOGGER.info("loginRequestUri: {}", loginRequestUri);

        httpServletResponse.setHeader("Location", loginRequestUri);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/oauth")
    public String oauth(@RequestParam("code") String code) {
        LOGGER.info("code: {}", code);

        String accessToken = kakaoApiService.getAccessToken(code);
        LOGGER.info("accessToken: {}", accessToken);

        User user = kakaoApiService.getUser(accessToken);
        LOGGER.info("user: {}", user);

        return "redirect:/";
    }
}
