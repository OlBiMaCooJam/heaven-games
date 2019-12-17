package com.olbimacoojam.heaven.yutnori.yutnorigame;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.yutnori.piece.OneOnOneStrategy;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.point.Points;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.YutThrowStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class YutnoriGame implements Game {
    private final YutThrowStrategy yutThrowStrategy;
    private List<YutnoriParticipant> yutnoriParticipants;
    private Board board;
    private Turn turn;

    public YutnoriGame(YutThrowStrategy yutThrowStrategy) {
        this.yutThrowStrategy = yutThrowStrategy;
    }

    @Override
    public void initialize(List<User> players) {
        yutnoriParticipants = createParticipants(players);
        turn = new Turn(yutnoriParticipants.get(0));
        board = new Board(new OneOnOneStrategy(yutnoriParticipants));
    }

    private List<YutnoriParticipant> createParticipants(List<User> players) {
        List<Color> colors = Arrays.asList(Color.values());
        return IntStream.range(0, players.size())
                .mapToObj(index -> new YutnoriParticipant(players.get(index), colors.get(index)))
                .collect(Collectors.toList());
    }

    public Yut throwYut(User thrower) {
        Yut yut = yutThrowStrategy.throwYut();
        return turn.saveOneThrow(thrower, yut);
    }

    public MoveResults move(User user, PointName pointName, Yut yut) {
        turn.consumeYut(user, yut);
        MoveVerifier moveVerifier = new MoveVerifier(turn.getTeamColor(), Points.get(pointName));
        MoveResults moveResults = board.move(moveVerifier, yut);
        turn = turn.next(moveResults);
        return moveResults;
    }
}
