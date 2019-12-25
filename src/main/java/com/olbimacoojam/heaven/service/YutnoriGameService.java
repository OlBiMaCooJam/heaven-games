package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.dto.*;
import com.olbimacoojam.heaven.yutnori.YutnoriGame;
import com.olbimacoojam.heaven.yutnori.board.Board;
import com.olbimacoojam.heaven.yutnori.piece.YutnoriGameResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YutnoriGameService {

    private final RoomService roomService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public YutnoriGameService(RoomService roomService, UserService userService, ModelMapper modelMapper) {
        this.roomService = roomService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public YutnoriStateResponse gameState(int roomId) {
        YutnoriGame yutnoriGame = getYutnoriGame(roomId);

        return new YutnoriStateResponse(
                getYutnoriParticipantsResponse(yutnoriGame),
                getBoardResponse(yutnoriGame));
    }

    private BoardResponse getBoardResponse(YutnoriGame yutnoriGame) {
        Board board = yutnoriGame.getBoard();
        return modelMapper.map(board, BoardResponse.class);
    }

    private List<YutnoriParticipantResponse> getYutnoriParticipantsResponse(YutnoriGame yutnoriGame) {
        return yutnoriGame.getYutnoriParticipants().stream()
                .map(participant -> modelMapper.map(participant, YutnoriParticipantResponse.class))
                .collect(Collectors.toList());
    }

    public YutResponse throwYut(int roomId, Long userId) {
        User thrower = userService.findById(userId);
        YutnoriGame yutnoriGame = getYutnoriGame(roomId);
        Yut yut = yutnoriGame.throwYut(thrower, () -> Yut.DO);
        return new YutResponse(yut.name());
    }

    public MoveResultDtos movePiece(int roomId, Long userId, MoveRequestDto moveRequestDto) {
        User mover = userService.findById(userId);
        YutnoriGame yutnoriGame = getYutnoriGame(roomId);
        PointName pointName = PointName.get(moveRequestDto.getPointName());
        Yut yut = Yut.get(moveRequestDto.getYut());
        MoveResults moveResults = yutnoriGame.move(mover, pointName, yut);
        YutnoriGameResult yutnoriGameResult = yutnoriGame.isGameOver();
        return new MoveResultDtos(moveResults, yutnoriGameResult);
    }

    private YutnoriGame getYutnoriGame(int roomId) {
        Room room = roomService.findById(roomId);
        return (YutnoriGame) room.getGame();
    }
}
