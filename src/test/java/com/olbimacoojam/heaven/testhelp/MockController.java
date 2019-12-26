package com.olbimacoojam.heaven.testhelp;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.service.NotFoundUserException;
import com.olbimacoojam.heaven.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/mock/login")
    public ResponseEntity mockLogin(HttpSession httpSession, @RequestBody Long kakaoId) {
        User mockUser = getMockUser(kakaoId);
        UserSession mockUserSession = getUserSession(mockUser);
        httpSession.setAttribute(UserSession.USER_SESSION, mockUserSession);

        return ResponseEntity.ok().build();
    }

    private User getMockUser(Long kakaoId) {
        try {
            return userService.findById(kakaoId);
        } catch (NotFoundUserException e) {
            long order = this.order.getAndIncrement();
            return userService.save(new User(kakaoId, "name" + order, "refreshToken" + order));
        }
    }

    private UserSession getUserSession(User user) {
        return new UserSession(user.getId(), user.getName(), "accessToken" + order.getAndIncrement());
    }
}
