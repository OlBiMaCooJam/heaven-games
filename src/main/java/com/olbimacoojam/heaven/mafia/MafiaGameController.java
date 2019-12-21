package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

import static com.olbimacoojam.heaven.interceptor.HttpHandshakeInterceptor.HTTP_SESSION;

@Controller
public class MafiaGameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MafiaGameController.class);

    private final RoomService roomService;

    public WebsocketMafiaController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms/{roomId}/mafia")
    public ResponseEntity startGame(@PathVariable int roomId) {
        int numberOfPlayers = roomService.startGame(roomId);

        return ResponseEntity.ok()
                .body(numberOfPlayers);
    }

    @MessageMapping("/rooms/{roomId}/mafia")
    @SendToUser("/queue/rooms/{roomId}/mafia/occupation")
    public MafiaOccupationMessage notifyOccupation(@DestinationVariable int roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);
        Long userId = userSession.getId();
        Room room = roomService.findById(roomId);
        MafiaParticipant mafiaParticipant = ((MafiaGame) room.getGame()).findMafiaParticipantByUserId(userId);
        return new MafiaOccupationMessage(userId, mafiaParticipant.getOccupationType());
    }

    @MessageMapping("/rooms/{roomId}/mafia/chat")
    @SendTo("/topic/rooms/{roomId}/mafia/chat")
    public MafiaChatMessage chat(@DestinationVariable int roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor, String message) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);
        return new MafiaChatMessage(userSession.getId(), userSession.getName(), message);
    }
}
