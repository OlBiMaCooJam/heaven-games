package common.game;

import lombok.Getter;

@Getter
public enum GameType {
    SCORE(3), WIN_LOSE(15), NONE(0);

    private Integer scorePerCount;

    GameType(Integer scorePerCount) {
        this.scorePerCount = scorePerCount;
    }
}
