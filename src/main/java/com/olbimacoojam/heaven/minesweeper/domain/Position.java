package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Position {
    private static final Integer MAX_Y = 30;
    private static final Integer MAX_X = 30;
    private final Integer x;
    private final Integer y;


    private Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(Integer x, Integer y) {
        return new Position(x, y);
    }

    public List<Position> getAroundPositions() {
        List<Position> positions = IntStream.rangeClosed(x - 1, x + 1)
                .boxed()
                .flatMap(numberX -> IntStream.rangeClosed(y - 1, y + 1)
                        .filter(numberY -> !numberX.equals(x) || numberY != y)
                        .mapToObj(numberY -> Position.of(numberX, numberY)))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(positions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) &&
                Objects.equals(y, position.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
