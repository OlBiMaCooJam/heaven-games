package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.yutnori.exception.IncorrectTurnException;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.YutThrowStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class YutnoriGame implements Game {

    private final YutThrowStrategy yutThrowStrategy;
    private Board board;
    private Turn turn;
    private List<YutnoriParticipant> yutnoriParticipants;

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
        if (turn.isRightTurn(thrower)) {
            return yutThrowStrategy.throwYut();
        }

        throw new IncorrectTurnException();
    }
}
