package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class MockController {
    private final UserService userService;
    private int order;

    public MockController(UserService userService) {
        this.userService = userService;
        order = 1;
    }

    @GetMapping("/mock/login")
    public ResponseEntity mockLogin(HttpSession httpSession) {
        User mockUser = new User(111111L + order, "mockUser" + order, "abcdef" + order);
        userService.save(mockUser);
        UserSession mockSession = new UserSession(mockUser.getId(), mockUser.getName(), "zxcvb" + order);
        httpSession.setAttribute(UserSession.USER_SESSION, mockSession);
        order++;
        return ResponseEntity.ok().build();
    }
}
