package com.olbimacoojam.heaven.testhelp;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MockController {
    private final UserService userService;
    private final AtomicLong order;

    public MockController(UserService userService) {
        this.userService = userService;
        order = new AtomicLong();
    }

    @GetMapping("/mock/login")
    public ResponseEntity mockLogin(HttpSession httpSession) {
        long currentOrder = order.getAndIncrement();
        User mockUser = new User(currentOrder, "mockUser" + currentOrder, "abcdef" + currentOrder);
//        userService.save(mockUser);
//        UserSession mockUserSession = new UserSession(mockUser.getId(), mockUser.getName(), "zxcvb" + currentOrder);
        httpSession.setAttribute(UserSession.USER_SESSION, mockUser);
        return ResponseEntity.ok().build();
    }
}
