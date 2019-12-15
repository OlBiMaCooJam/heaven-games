package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;

import java.util.Objects;

public class MoveResult {
    private final Piece piece;
    private Route route;

    public MoveResult(Piece piece, Route route) {
        this.piece = piece;
        this.route = route;
    }

    public Point findDestination() {
        return route.getDestination();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveResult that = (MoveResult) o;
        return Objects.equals(piece, that.piece) &&
                Objects.equals(route, that.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, route);
    }
}
