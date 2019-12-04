package com.olbimacoojam.heaven.game;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Embeddable
public class Result {

    @Enumerated(EnumType.STRING)
    private WinLose winLose;

    private Integer count;

    private Result(WinLose winLose) {
        if (winLose == null) {
            throw new InvalidGameResultException();
        }
        this.winLose = winLose;
    }

    private Result(Integer count) {
        if (count == null) {
            throw new InvalidGameResultException();
        }
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
