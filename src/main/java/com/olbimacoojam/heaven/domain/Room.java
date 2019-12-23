package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.dto.GameStartResponseDtos;
import com.olbimacoojam.heaven.dto.MoveResultDtos;
import com.olbimacoojam.heaven.dto.YutResponse;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.yutnori.YutnoriGame;
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

    public void leave() {
        players.remove(0);
    }

    public void startGame() {
        game.initialize(players);
    }

    public GameStartResponseDtos initiateGame() {
        game.initialize(players);
        return ((YutnoriGame) game).getStartingStatus();
    }

    public YutResponse throwYut(User thrower) {
        Yut yut = ((YutnoriGame) game).throwYut(thrower, () -> Yut.DO);
        return new YutResponse(yut.name());
    }

    public MoveResultDtos movePiece(User mover, PointName pointName, Yut yut) {
        return ((YutnoriGame) game).move(mover, pointName, yut);
    }
}
