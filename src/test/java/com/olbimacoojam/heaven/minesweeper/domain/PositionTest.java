package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {
    @Test
    void getAroundPositions() {
        Position middle = Position.of(1, 1);

        List<Position> expected = Arrays.asList(
                Position.of(0, 0), Position.of(1, 0), Position.of(2, 0),
                Position.of(0, 1), Position.of(2, 1),
                Position.of(0, 2), Position.of(1, 2), Position.of(2, 2)
        );

        List<Position> aroundPositions = middle.getAroundPositions();

        assertThat(aroundPositions.size()).isEqualTo(expected.size());
        aroundPositions.forEach(position -> assertThat(expected.contains(position)).isTrue());
    }
}