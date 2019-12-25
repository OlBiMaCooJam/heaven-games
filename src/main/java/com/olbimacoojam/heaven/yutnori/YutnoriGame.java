package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.yutnori.board.Board;
import com.olbimacoojam.heaven.yutnori.board.BoardCreateStrategy;
import com.olbimacoojam.heaven.yutnori.participant.YutnoriParticipant;
import com.olbimacoojam.heaven.yutnori.participant.YutnoriParticipants;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.turn.Turn;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.YutThrowStrategy;
import lombok.Getter;

import java.util.List;

@Getter
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
        yutnoriParticipants = YutnoriParticipants.of(players);
        turn = new Turn(yutnoriParticipants.getFirst());
        board = boardCreateStrategy.createBoard(yutnoriParticipants);
    }

    public Yut throwYut(User thrower, YutThrowStrategy yutThrowStrategy) {
        Yut yut = yutThrowStrategy.throwYut();
        turn = turn.saveOneThrow(thrower, yut);
        return yut;
    }

    public MoveResults move(User user, PointName pointName, Yut yut) {
        turn.checkMove(user, yut);

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

    public List<YutnoriParticipant> getYutnoriParticipants() {
        return yutnoriParticipants.getYutnoriParticipants();
    }

    public YutnoriGameResult isGameOver() {
        if (yutnoriParticipants.isGameOver()) {
            return new YutnoriGameResult("게임이 끝났습니다!", yutnoriParticipants.getWinners());
        }
        return new YutnoriGameResult("게임이 진행중입니다!", yutnoriParticipants.getWinners());
    }

    public Boolean isFinish(User user) {
        return yutnoriParticipants.isFinish(user);
    }
}
