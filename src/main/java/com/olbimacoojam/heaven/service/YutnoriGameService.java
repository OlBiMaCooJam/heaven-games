package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.dto.MoveRequestDto;
import com.olbimacoojam.heaven.dto.MoveResultDtos;
import com.olbimacoojam.heaven.dto.YutResponse;
import com.olbimacoojam.heaven.yutnori.YutnoriGame;
import com.olbimacoojam.heaven.yutnori.piece.YutnoriGameResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.springframework.stereotype.Service;

@Service
public class YutnoriGameService {
    private final RoomService roomService;
    private final UserService userService;

    public YutnoriGameService(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    public YutResponse throwYut(int roomId, Long userId) {
        Room room = roomService.findById(roomId);
        User thrower = userService.findById(userId);
        YutnoriGame yutnoriGame = (YutnoriGame) room.getGame();
        Yut yut = yutnoriGame.throwYut(thrower, () -> Yut.DO);
        return new YutResponse(yut.name());
    }

    public MoveResultDtos movePiece(int roomId, Long userId, MoveRequestDto moveRequestDto) {
        Room room = roomService.findById(roomId);
        User mover = userService.findById(userId);
        YutnoriGame yutnoriGame = (YutnoriGame) room.getGame();
        PointName pointName = PointName.get(moveRequestDto.getPointName());
        Yut yut = Yut.get(moveRequestDto.getYut());
        MoveResults moveResults = yutnoriGame.move(mover, pointName, yut);
        YutnoriGameResult yutnoriGameResult = yutnoriGame.isGameOver();
        return new MoveResultDtos(moveResults, yutnoriGameResult);
    }
}
