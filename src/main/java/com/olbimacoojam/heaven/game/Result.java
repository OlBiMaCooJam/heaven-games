package com.olbimacoojam.heaven.game;

import lombok.Getter;

@Getter
public class Result {
    private WinLose winLose;
    private Integer count;

    private Result(WinLose winLose) {
        this.winLose = winLose;
    }

    private Result(Integer count) {
        this.count = count;
    }

    public static Result of(WinLose winLose) {
        return new Result(winLose);
    }

    public static Result of(Integer count) {
        return new Result(count);
    }

    public boolean isScoreType(GameKind gameKind) {
        return gameKind.isScoreType();
    }
}
