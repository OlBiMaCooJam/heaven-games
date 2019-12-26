package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.game.GameKind2;
import com.olbimacoojam.heaven.mafia.MafiaGame;
import com.olbimacoojam.heaven.yutnori.YutnoriGame;
import com.olbimacoojam.heaven.yutnori.board.OneOnOneStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Component
public class RoomFactory {

    private final AtomicInteger id = new AtomicInteger();

    private final Map<GameKind2, Supplier<Game>> gameMap = new HashMap<>();

    {
        gameMap.put(GameKind2.MAFIA, MafiaGame::new);
        gameMap.put(GameKind2.YUTNORI, () -> new YutnoriGame(new OneOnOneStrategy()));
        gameMap.put(GameKind2.MINE, () -> new YutnoriGame(new OneOnOneStrategy()));
    }

    public Room makeNextRoom(GameKind2 gameKind) {
        int roomId = id.getAndIncrement();
        Game game = createGame(gameKind);
        return new Room(roomId, game);
    }

    public Game createGame(GameKind2 gameKind) {
        return gameMap.get(gameKind).get();
    }
}
