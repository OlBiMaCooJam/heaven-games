package com.olbimacoojam.heaven.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ResultTest {
    @Test
    void countNullCheck() {
        Integer count = null;
        assertThrows(InvalidGameResultException.class, () -> Result.of(count));
    }

    @Test
    void winLoseNullCheck() {
        WinLose winLose = null;
        assertThrows(InvalidGameResultException.class, () -> Result.of(winLose));
    }
}