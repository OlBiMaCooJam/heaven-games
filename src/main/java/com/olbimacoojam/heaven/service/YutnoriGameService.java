package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.dto.*;
import com.olbimacoojam.heaven.yutnori.YutnoriGame;
import com.olbimacoojam.heaven.yutnori.board.Board;
import com.olbimacoojam.heaven.yutnori.YutnoriGameResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.YutThrowStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YutnoriGameService {

    private final RoomService roomService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final YutThrowStrategy yutThrowStrategy;

    public YutnoriGameService(RoomService roomService, UserService userService, ModelMapper modelMapper, YutThrowStrategy yutThrowStrategy) {
        this.roomService = roomService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.yutThrowStrategy = yutThrowStrategy;
    }

    public YutnoriStateResponse gameState(int roomId) {
        YutnoriGame yutnoriGame = getYutnoriGame(roomId);

        return new YutnoriStateResponse(
                getYutnoriParticipantResponses(yutnoriGame),
                getBoardResponse(yutnoriGame),
                getTurnResposne(yutnoriGame));
    }

    private TurnResponse getTurnResposne(YutnoriGame yutnoriGame) {
        return modelMapper.map(yutnoriGame.getTurn(), TurnResponse.class);
    }

    private BoardResponse getBoardResponse(YutnoriGame yutnoriGame) {
        Board board = yutnoriGame.getBoard();
        return modelMapper.map(board, BoardResponse.class);
    }

    private List<YutnoriParticipantResponse> getYutnoriParticipantResponses(YutnoriGame yutnoriGame) {
        return yutnoriGame.getYutnoriParticipants().stream()
                .map(participant -> modelMapper.map(participant, YutnoriParticipantResponse.class))
                .collect(Collectors.toList());
    }

    public YutResponse throwYut(int roomId, Long userId) {
        YutnoriGame yutnoriGame = getYutnoriGame(roomId);
        User thrower = userService.findById(userId);

        Yut yut = yutnoriGame.throwYut(thrower, yutThrowStrategy);
        return new YutResponse(yut, getTurnResposne(yutnoriGame));
    }

    public MoveResponse movePiece(int roomId, Long userId, MoveRequestDto moveRequestDto) {
        YutnoriGame yutnoriGame = getYutnoriGame(roomId);

        User mover = userService.findById(userId);
        PointName sourcePointName = moveRequestDto.getSourcePoint();
        Yut yut = moveRequestDto.getYut();

        MoveResults moveResults = yutnoriGame.move(mover, sourcePointName, yut);

        MoveResultsDto moveResultsDto = modelMapper.map(moveResults, MoveResultsDto.class);
        YutnoriGameResult yutnoriGameResult = yutnoriGame.isGameOver();
        return new MoveResponse(moveResultsDto, yutnoriGameResult, getTurnResposne(yutnoriGame));
    }

    private YutnoriGame getYutnoriGame(int roomId) {
        Room room = roomService.findById(roomId);
        return (YutnoriGame) room.getGame();
    }
}
