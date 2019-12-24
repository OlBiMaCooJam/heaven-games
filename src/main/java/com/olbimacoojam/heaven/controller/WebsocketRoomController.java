package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

import static com.olbimacoojam.heaven.interceptor.HttpHandshakeInterceptor.HTTP_SESSION;

@Controller
public class WebsocketRoomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketRoomController.class);
    private final RoomService roomService;

    public WebsocketRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @MessageMapping("/rooms/{roomId}")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto enterRoom(@DestinationVariable Long roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);
        Long userId = userSession.getId();
        LOGGER.info("roomId: {}", roomId);
        LOGGER.info("userId: {}", userId);
        return roomService.subscribe(roomId, userId);
    }

    @MessageMapping("/rooms/{roomId}/leave/{userId}")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto leaveRoom(@DestinationVariable Long roomId, @DestinationVariable Long userId) {
        return roomService.unsubscribe(roomId, userId);
    }

    @MessageMapping("/rooms/{roomId}/start")
    @SendTo("/topic/rooms/{roomId}/start")
    public RoomResponseDto startGame(@DestinationVariable Long roomId) {
        Room room = roomService.findById(roomId);
        roomService.findById(roomId).getGame().initialize(room.getPlayers());
        return new RoomResponseDto();
    }
//
//    @MessageMapping("/rooms/{roomId}")
//    @SendTo("/topic/rooms/{roomId}")
//    public List<String> showUserList(@DestinationVariable Long roomId) {
//        Room room = roomService.findById(roomId);
//        List<User> players = room.getPlayers();
//        return players.stream().map(User::getName).collect(Collectors.toList());
//    }
}
