package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.service.RoomService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

import static com.olbimacoojam.heaven.HttpHandshakeInterceptor.HTTP_SESSION;

@Controller
public class WebsocketRoomController {
    private final RoomService roomService;

    public WebsocketRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @MessageMapping("/rooms/{roomId}")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto enterRoom(@DestinationVariable int roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        User user = (User) httpSession.getAttribute(UserSession.USER_SESSION);
        return roomService.subscribe(roomId, user);
    }

//    public MafiaOccupationMessage notifyOccupation(@DestinationVariable Long roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
//        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
//        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);
//        Long userId = userSession.getId();
//        Room room = roomService.findById(roomId);
//        MafiaParticipant mafiaParticipant = ((MafiaGame) room.getGame()).findMafiaParticipantByUserId(userId);
//        return new MafiaOccupationMessage(userId, mafiaParticipant.getOccupationType());
//    }

    @MessageMapping("/rooms/{roomId}/leave")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto leaveRoom(@DestinationVariable int roomId) {
        return roomService.unsubscribe(roomId);
    }
}
