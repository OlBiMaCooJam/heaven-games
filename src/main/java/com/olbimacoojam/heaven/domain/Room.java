package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.game.GameKind2;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//todo : input 유효성 체크하기
@Getter
@ToString
public class Room {

    private static final Logger log = LoggerFactory.getLogger(Room.class);

    private final int id;
    private final List<User> players;
    private final Game game;

    public Room(int id, Game game) {
        this.players = new CopyOnWriteArrayList<>();
        this.id = id;
        this.game = game;
    }

    public void join(User player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void leave(User user) {
        players.remove(user);
    }

    public boolean startGame() {
        try {
            game.initialize(players);
            return true;
        } catch (RuntimeException e) {
            log.error("fail to start game", e);
            return false;
        }
    }

    public int countPlayers() {
        return players.size();
    }

    public GameKind2 gameKind2() {
        return GameKind2.of(this.game);
    }

    public boolean isGameKind2(GameKind2 gameKind2) {
        return gameKind2.is(this.game);
    }
}
