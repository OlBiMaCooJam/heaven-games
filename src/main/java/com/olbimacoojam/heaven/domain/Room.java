package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.yutnori.YutnoriGame;
import com.olbimacoojam.heaven.yutnori.participant.YutnoriParticipant;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//todo : input 유효성 체크하기
@Getter
public class Room {
    private final int id;
    private List<User> players;
    private final Game game;

    public Room(int id, Game game) {
        this.players = new CopyOnWriteArrayList<>();
        this.id = id;
        this.game = game;
    }

    public void join(User player) {
        players.add(player);
        players.sort(Comparator.comparing(User::getName));
    }

    public void leave(User user) {
        players.remove(user);
    }

    public void startGame() {
        game.initialize(players);
    }

    public List<YutnoriParticipant> initiateGame() {
        game.initialize(players);
        return ((YutnoriGame) game).getYutnoriParticipants();
    }

    public Yut throwYut(User thrower) {
        Yut yut = ((YutnoriGame) game).throwYut(thrower, () -> Yut.DO);
        return yut;
    }

    public MoveResults movePiece(User mover, PointName pointName, Yut yut) {
        return ((YutnoriGame) game).move(mover, pointName, yut);
    }
}
