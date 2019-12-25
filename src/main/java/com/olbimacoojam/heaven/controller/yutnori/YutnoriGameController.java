package com.olbimacoojam.heaven.controller.yutnori;

import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.dto.MoveRequestDto;
import com.olbimacoojam.heaven.dto.MoveResultDtos;
import com.olbimacoojam.heaven.dto.YutResponse;
import com.olbimacoojam.heaven.dto.YutnoriStateResponse;
import com.olbimacoojam.heaven.service.YutnoriGameService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

import static com.olbimacoojam.heaven.HttpHandshakeInterceptor.HTTP_SESSION;

@Controller
public class YutnoriGameController {

    private YutnoriGameService yutnoriGameService;

    public YutnoriGameController(YutnoriGameService yutnoriGameService) {
        this.yutnoriGameService = yutnoriGameService;
    }

    @MessageMapping("/yutnori/{roomId}")
    @SendTo("/topic/yutnori/{roomId}")
    public YutnoriStateResponse gameState(@DestinationVariable int roomId) {
        return yutnoriGameService.gameState(roomId);
    }

    @MessageMapping("/yutnori/{roomId}/move-piece")
    @SendTo("/topic/yutnori/{roomId}/playing")
    public MoveResultDtos movePiece(@DestinationVariable int roomId, MoveRequestDto moveRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        Long userId = getUserId(simpMessageHeaderAccessor);
        return yutnoriGameService.movePiece(roomId, userId, moveRequestDto);
    }

    @MessageMapping("/yutnori/{roomId}/yut-throw")
    @SendTo("/topic/yutnori/{roomId}/yut-throw")
    public YutResponse throwYut(@DestinationVariable int roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        Long userId = getUserId(simpMessageHeaderAccessor);
        return yutnoriGameService.throwYut(roomId, userId);
    }

    public Long getUserId(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);
        return userSession.getId();
    }
}
