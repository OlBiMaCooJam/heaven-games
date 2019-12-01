package com.olbimacoojam.heaven.game;

import lombok.Getter;

import static com.olbimacoojam.heaven.game.GameType.NONE;
import static com.olbimacoojam.heaven.game.GameType.SCORE;
import static com.olbimacoojam.heaven.game.GameType.WIN_LOSE;

@Getter
public enum GameKinds {
    MINE(WIN_LOSE), MAFIA(WIN_LOSE), SONG(SCORE), BASEBALL(NONE), LADDER(NONE), DRAW(NONE);

    private GameType gameType;

    GameKinds(GameType gameType) {
        this.gameType = gameType;
    }

    public Integer getScorePerCount() {
        return gameType.getScorePerCount();
    }
}
