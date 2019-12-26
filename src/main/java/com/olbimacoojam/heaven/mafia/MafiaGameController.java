package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;
import com.olbimacoojam.heaven.service.RoomService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.olbimacoojam.heaven.interceptor.HttpHandshakeInterceptor.HTTP_SESSION;

@Controller
public class MafiaGameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MafiaGameController.class);

    private final RoomService roomService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MafiaGameController(RoomService roomService, SimpMessagingTemplate simpMessagingTemplate) {
        this.roomService = roomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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

    //TODO: 의사가 군인을 선택했을 경우
    @MessageMapping("/rooms/{roomId}/mafia/chat")
    @SendTo("/topic/rooms/{roomId}/mafia/chat")
    public MafiaChatMessage chat(@DestinationVariable Long roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor, String message) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);

        MafiaGame mafiaGame = (MafiaGame) roomService.findById(roomId).getGame();

        boolean isAlive = mafiaGame.findMafiaParticipantByUserId(userSession.getId()).isAlive();

        if (!mafiaGame.isDay() || !isAlive) {
            throw new NotAllowChatException();
        }

        return new MafiaChatMessage(userSession.getId(), userSession.getName(), message);
    }

    @MessageMapping("/rooms/{roomId}/mafia/chat/mafiaOnly")
    @SendTo("/topic/rooms/{roomId}/mafia/chat/mafiaOnly")
    public MafiaChatMessage chatMafiaOnly(@DestinationVariable Long roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor, String message) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);

        MafiaGame mafiaGame = (MafiaGame) roomService.findById(roomId).getGame();

        OccupationType occupationType = mafiaGame.findMafiaParticipantByUserId(userSession.getId()).getOccupationType();
        boolean isAlive = mafiaGame.findMafiaParticipantByUserId(userSession.getId()).isAlive();

        if (!isAlive || mafiaGame.isDay() || !occupationType.equals(OccupationType.MAFIA)) {
            throw new NotAllowChatException();
        }

        return new MafiaChatMessage(userSession.getId(), userSession.getName(), message);
    }

    @MessageMapping("/rooms/{roomId}/vote")
    @SendToUser("/queue/rooms/{roomId}/vote")
    public List<MafiaParticipantName> vote(@DestinationVariable Long roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);

        MafiaGame mafiaGame = (MafiaGame) roomService.findById(roomId).getGame();
        boolean isAlive = mafiaGame.findMafiaParticipantByUserId(userSession.getId()).isAlive();

        if (!isAlive) {
            throw new NotAllowVoteException("죽은 사람 투표 요청");
        }

        Room room = roomService.findById(roomId);
        List<MafiaParticipant> aliveMafiaParticipants = ((MafiaGame) room.getGame()).findAliveMafiaParticipants();
        return aliveMafiaParticipants.stream()
                .map(mafiaParticipant -> new MafiaParticipantName(mafiaParticipant.getPlayer().getName()))
                .collect(Collectors.toList());
    }

    @MessageMapping("/rooms/{roomId}/select")
    @SendTo("/topic/rooms/{roomId}/dayResult")
    public String daySelect(@DestinationVariable Long roomId, MafiaParticipantName mafiaParticipantName) {
        MafiaGame game = (MafiaGame) roomService.findById(roomId).getGame();

        List<MafiaParticipant> mafiaParticipants = game.findAliveMafiaParticipants();
        MafiaParticipant selectedMafiaParticipant = mafiaParticipants.stream()
                .filter(mafiaParticipant -> mafiaParticipant.getPlayer().getName().equals(mafiaParticipantName.getName()))
                .findFirst()
                .orElseThrow(NotFoundMafiaParticipantException::new);

        boolean IsFinish = game.voteParticipant(selectedMafiaParticipant);
        if (IsFinish) {
            return game.showDayResult();
        }
        return null;
    }

    @MessageMapping("/rooms/{roomId}/MAFIA")
    public void mafiaPerform(@DestinationVariable Long roomId, MafiaParticipantName mafiaParticipantName, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);

        MafiaGame game = (MafiaGame) roomService.findById(roomId).getGame();

        MafiaParticipant selector = game.findMafiaParticipantByUserId(userSession.getId());

        MafiaParticipant selectedMafiaParticipant = getSelectedParticipant(selector, game, mafiaParticipantName);
        game.voteParticipant(selectedMafiaParticipant);

        if (game.isAllPerform()) {
            simpMessagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/nightResult", game.showNightResult());
        }
    }

    @MessageMapping("/rooms/{roomId}/DOCTOR")
    public void doctorPerform(@DestinationVariable Long roomId, MafiaParticipantName mafiaParticipantName, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);

        MafiaGame game = (MafiaGame) roomService.findById(roomId).getGame();

        MafiaParticipant selector = game.findMafiaParticipantByUserId(userSession.getId());

        MafiaParticipant selectedMafiaParticipant = getSelectedParticipant(selector, game, mafiaParticipantName);
        selectedMafiaParticipant.applyShield();

        if (game.isAllPerform()) {
            simpMessagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/nightResult", game.showNightResult());
        }
    }

    @MessageMapping("/rooms/{roomId}/POLICE")
    @SendToUser("/queue/rooms/{roomId}/POLICE")
    public String policePerform(@DestinationVariable Long roomId, MafiaParticipantName mafiaParticipantName, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        HttpSession httpSession = (HttpSession) (simpMessageHeaderAccessor.getSessionAttributes().get(HTTP_SESSION));
        UserSession userSession = (UserSession) httpSession.getAttribute(UserSession.USER_SESSION);

        MafiaGame game = (MafiaGame) roomService.findById(roomId).getGame();

        MafiaParticipant selector = game.findMafiaParticipantByUserId(userSession.getId());

        MafiaParticipant selectedMafiaParticipant = getSelectedParticipant(selector, game, mafiaParticipantName);

        if (game.isAllPerform()) {
            simpMessagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/nightResult", game.showNightResult());
        }

        return selectedMafiaParticipant.getOccupationType().equals(OccupationType.MAFIA) ? "마피아 O" : "마피아 X";
    }

    @MessageMapping("/rooms/{roomId}/DETECTIVE")
    @SendToUser("/queue/rooms/{roomId}/DETECTIVE")
    public String detectivePerform(@DestinationVariable Long roomId, MafiaParticipantName mafiaParticipantName) {
        MafiaGame game = (MafiaGame) roomService.findById(roomId).getGame();

        if (game.isAllPerform()) {
            simpMessagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/nightResult", game.showNightResult());
        }

        return game.getSelectData(mafiaParticipantName.getName());
    }

    @MessageMapping("/rooms/{roomId}/nightResult")
    @SendTo("/topic/rooms/{roomId}/nightResult")
    public String nightResult(@DestinationVariable Long roomId, MafiaParticipantName mafiaParticipantName) {
        MafiaGame game = (MafiaGame) roomService.findById(roomId).getGame();
        return game.showNightResult();
    }

    @MessageMapping("/rooms/{roomId}/list")
    @SendTo("/topic/rooms/{roomId}/list")
    public List<MafiaParticipantInfo> list(@DestinationVariable Long roomId) {
        LOGGER.info("asd");
        MafiaGame game = (MafiaGame) roomService.findById(roomId).getGame();
        return game.getMafiaParticipants().stream()
                .map(mafiaParticipant -> new MafiaParticipantInfo(mafiaParticipant.getPlayer().getName(), mafiaParticipant.isAlive()))
                .collect(Collectors.toList());
    }

    //TODO: 중복이름 처리
    private MafiaParticipant getSelectedParticipant(MafiaParticipant selector, MafiaGame game, MafiaParticipantName selectedName) {
        List<MafiaParticipant> mafiaParticipants = game.findAliveMafiaParticipants();
        MafiaParticipant selected = mafiaParticipants.stream()
                .filter(mafiaParticipant -> mafiaParticipant.getPlayer().getName().equals(selectedName.getName()))
                .findFirst()
                .orElseThrow(NotFoundMafiaParticipantException::new);

        game.checkSelect(selector, selected);
        return selected;
    }
}
