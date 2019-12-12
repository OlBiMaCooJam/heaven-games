package com.olbimacoojam.heaven.minesweeper.domain;

import java.util.Objects;

public class Position {
    private final Integer x;
    private final Integer y;

    private Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(Integer x, Integer y) {
        return new Position(x, y);
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
