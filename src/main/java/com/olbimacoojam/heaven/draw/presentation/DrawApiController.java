package com.olbimacoojam.heaven.draw.presentation;

import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.draw.application.DrawCreateRequest;
import com.olbimacoojam.heaven.draw.application.DrawResponse;
import com.olbimacoojam.heaven.draw.application.DrawService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/rooms/{roomId}/draw")
public class DrawApiController {

    private final DrawService drawService;

    public DrawApiController(DrawService drawService) {
        this.drawService = drawService;
    }

    @PostMapping
    public ResponseEntity<DrawResponse> initGame(HttpSession httpSession, @PathVariable Long roomId) {
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);

        return ResponseEntity.ok()
                .body(drawService.initGame(userSession.getId(), roomId));
    }

    @PutMapping
    public ResponseEntity<DrawResponse> updateGame(HttpSession httpSession, @PathVariable Long roomId, @RequestBody DrawCreateRequest drawCreateRequest) {
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);

        return ResponseEntity.ok()
                .body(drawService.updateGame(userSession.getId(), roomId, drawCreateRequest));
    }
}
