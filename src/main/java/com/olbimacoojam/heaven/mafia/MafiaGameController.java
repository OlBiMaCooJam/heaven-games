package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

import static com.olbimacoojam.heaven.interceptor.HttpHandshakeInterceptor.HTTP_SESSION;

@Controller
public class MafiaGameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MafiaGameController.class);

    private final RoomService roomService;

    public MafiaGameController(RoomService roomService) {
        this.roomService = roomService;
    }

    @MessageMapping("/rooms/{roomId}/mafia")
    @SendToUser("/queue/rooms/{roomId}/mafia/occupation")
    public MafiaOccupationMessage notifyOccupation(@DestinationVariable Long roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);
        Long userId = userSession.getId();
        Room room = roomService.findById(roomId);
        MafiaParticipant mafiaParticipant = ((MafiaGame) room.getGame()).findMafiaParticipantByUserId(userId);
        return new MafiaOccupationMessage(userId, mafiaParticipant.getOccupationType());
    }

    @MessageMapping("/rooms/{roomId}/mafia/chat")
    @SendTo("/topic/rooms/{roomId}/mafia/chat")
    public MafiaChatMessage chat(SimpMessageHeaderAccessor simpMessageHeaderAccessor, String message) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);
        return new MafiaChatMessage(userSession.getId(), userSession.getName(), message);
    }
}
