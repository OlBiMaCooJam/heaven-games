package com.olbimacoojam.heaven.game;

import lombok.Getter;

@Getter
public enum Result {
    WIN(1), LOSE(0), COUNT(0);

    private Integer count;

    Result(Integer count) {
        this.count = count;
    }

    public Integer getTotalScore(GameKinds gameKinds) {
        return gameKinds.getScorePerCount() * count;
    }
}
