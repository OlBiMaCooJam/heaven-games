package com.olbimacoojam.heaven.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameResultTest {

    @Test
    @DisplayName("전체 점수 계산")
    void getTotalScore() {
        int solveCount = 10;
        GameResult gameResult = new GameResult(GameKind.SONG, new User(), Result.of(solveCount));

        assertThat(gameResult.getTotalScore()).isEqualTo(GameType.SCORE.getScorePerCount() * solveCount);
    }

    @Test
    @DisplayName("잘못된 게임 타입")
    void invalidGameType() {
        GameResult gameResult = new GameResult(GameKind.MINE, new User(), Result.of(WinLose.WIN));

        assertThrows(InvalidGameTypeException.class, gameResult::getTotalScore);
    }
}