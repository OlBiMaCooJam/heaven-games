package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MinePositionsTest {
    @Test
    void isMine() {
        Set<Position> positions = new HashSet<>(Arrays.asList(Position.of(0, 0), Position.of(1, 1), Position.of(2, 2)));
        MinePositions minePositions = new MinePositions(positions);
        assertThat(minePositions.isMine(Position.of(0, 0))).isTrue();
        assertThat(minePositions.isMine(Position.of(1, 1))).isTrue();
        assertThat(minePositions.isMine(Position.of(2, 2))).isTrue();
        assertThat(minePositions.isMine(Position.of(3, 3))).isFalse();
    }
}