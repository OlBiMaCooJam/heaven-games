package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.game.GameKind;
import com.olbimacoojam.heaven.mafia.MafiaGame;
import com.olbimacoojam.heaven.minesweeper.domain.Minesweeper;
import com.olbimacoojam.heaven.yutnori.YutnoriGame;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//todo : input 유효성 체크하기
@Getter
public class Room {

    private static final Logger log = LoggerFactory.getLogger(Room.class);

    private final int id;
    private final Game game;
    private List<User> players;

    public Room(int id, Game game) {
        this.players = new CopyOnWriteArrayList<>();
        this.id = id;
        this.game = game;
    }

    public void join(User player) {
        if (!players.contains(player))
            players.add(player);
        players.sort(Comparator.comparing(User::getName));
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

    public GameKind getGameKind() {
        if (game instanceof YutnoriGame) {
            return GameKind.YUTNORI;
        }
        if (game instanceof MafiaGame) {
            return GameKind.MAFIA;
        }
        if (game instanceof Minesweeper) {
            return GameKind.MINE;
        }
        throw new IllegalStateException();
    }

    public boolean isGameKind(GameKind gameKind) {
        return getGameKind().equals(gameKind);
    }
}
