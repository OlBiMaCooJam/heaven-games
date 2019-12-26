package com.olbimacoojam.heaven.draw.application;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.draw.domain.Draw;
import com.olbimacoojam.heaven.service.RoomService;
import com.olbimacoojam.heaven.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DrawService {

    private final RoomService roomService;
    private final UserService userService;

    public DrawService(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    public DrawResponse updateGame(long userId, long roomId, DrawCreateRequest drawCreateRequest) {
        Draw draw = getDraw(userId, roomId);
        draw.startGame(drawCreateRequest.getPersonCount(), drawCreateRequest.getWhackCount());

        return new DrawResponse(draw.getLots());
    }

    public DrawResponse initGame(long userId, long roomId) {
        Draw draw = getDraw(userId, roomId);

        return new DrawResponse(draw.getLots());
    }

    private Draw getDraw(long userId, long roomId) {
        Room room = roomService.findById(roomId);
        Draw draw = (Draw) room.getGame();
        User user = userService.findById(userId);

        draw.initialize(Collections.singletonList(user));
        return draw;
    }
}
