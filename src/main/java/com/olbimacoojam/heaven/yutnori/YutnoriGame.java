package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.dto.GameStartResponseDto;
import com.olbimacoojam.heaven.dto.GameStartResponseDtos;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.yutnori.board.Board;
import com.olbimacoojam.heaven.yutnori.board.BoardCreateStrategy;
import com.olbimacoojam.heaven.yutnori.exception.IllegalTurnException;
import com.olbimacoojam.heaven.yutnori.participant.YutnoriParticipants;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.YutThrowStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class YutnoriGame implements Game {
    private final BoardCreateStrategy boardCreateStrategy;
    private YutnoriParticipants yutnoriParticipants;
    private Board board;
    private Turn turn;

    public YutnoriGame(BoardCreateStrategy boardCreateStrategy) {
        this.boardCreateStrategy = boardCreateStrategy;
    }

    @Override
    public void initialize(List<User> players) {
        yutnoriParticipants = new YutnoriParticipants(players);
        turn = new Turn(yutnoriParticipants.getFirst());
        board = boardCreateStrategy.createBoard(yutnoriParticipants);
    }

    public Yut throwYut(User thrower, YutThrowStrategy yutThrowStrategy) {
        Yut yut = yutThrowStrategy.throwYut();
        return turn.saveOneThrow(thrower, yut);
    }

    public MoveResults move(User user, PointName pointName, Yut yut) {
        checkTurn(user, yut);

        Color teamColor = turn.getTeamColor();

        MoveVerifier moveVerifier = MoveVerifier.of(teamColor, pointName);
        MoveResults moveResults = board.move(moveVerifier, yut);
        turn.removeYut(yut);

        if (board.isComplete(teamColor)) {
            yutnoriParticipants.finish(teamColor);
        }

        turn = turn.next(moveResults, yutnoriParticipants);

        return moveResults;
    }

    private void checkTurn(User user, Yut yut) {
        if (turn.canMove(user, yut)) {
            return;
        }
        throw new IllegalTurnException();
    }

    public GameStartResponseDtos getStartingStatus() {
        List<GameStartResponseDto> gameStartResponseDtos = yutnoriParticipants.stream()
                .map(yutnoriParticipant -> yutnoriParticipant.getGameStartResponseDto())
                .collect(Collectors.toList());
        return new GameStartResponseDtos(gameStartResponseDtos);
    }
}
