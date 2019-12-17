package com.olbimacoojam.heaven.yutnori.piece.moveresult;

import com.olbimacoojam.heaven.yutnori.piece.Piece;

import java.util.Objects;

public class MoveResult {
    private final Piece piece;
    private final Route route;

    public MoveResult(Piece piece, Route route) {
        this.piece = piece;
        this.route = route;
    }

    public boolean canCatch(Piece piece) {
        return this.piece.canCatch(piece);
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
