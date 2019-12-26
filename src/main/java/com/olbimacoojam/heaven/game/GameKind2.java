package com.olbimacoojam.heaven.game;

import com.olbimacoojam.heaven.draw.domain.Draw;
import com.olbimacoojam.heaven.mafia.MafiaGame;
import com.olbimacoojam.heaven.minesweeper.domain.Minesweeper;
import com.olbimacoojam.heaven.yutnori.YutnoriGame;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameKind2 {

    YUTNORI(YutnoriGame.class),
    MINE(Minesweeper.class),
    MAFIA(MafiaGame.class),
    DRAW(Draw.class);

    private Class<? extends Game> concreteGame;

    GameKind2(Class<? extends Game> concreteGame) {
        this.concreteGame = concreteGame;
    }

    public static GameKind2 of(Game game) {
        return Arrays.stream(values())
                .filter(gameKind -> gameKind.is(game))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean is(Game game) {
        return game.getClass().equals(this.concreteGame);
    }
}
