package common.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameKindTest {

    @Test
    @DisplayName("점수 타입의 게임인지 확인")
    void isScoreType() {
        assertTrue(GameKind.SONG.isScoreType());
        assertFalse(GameKind.MAFIA.isScoreType());
    }
}