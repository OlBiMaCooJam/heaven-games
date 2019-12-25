package com.olbimacoojam.heaven.draw.application;

import com.olbimacoojam.heaven.draw.domain.Lots;
import com.olbimacoojam.heaven.service.RoomService;
import com.olbimacoojam.heaven.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DrawService {

    private final RoomService roomService;
    private final UserService userService;

    public DrawService(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    public DrawResponse initialize(long userId, long roomId, DrawCreateRequest drawCreateRequest) {
        Lots lots = Lots.of(drawCreateRequest.getPersonCount(), drawCreateRequest.getWhackCount());

        return new DrawResponse(lots);
    }
}
