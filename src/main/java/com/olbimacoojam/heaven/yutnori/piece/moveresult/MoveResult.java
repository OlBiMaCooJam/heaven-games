package com.olbimacoojam.heaven.yutnori.piece.moveresult;

import com.olbimacoojam.heaven.yutnori.Color;
import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
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

    public boolean areYouCaught() {
        return route.isCaught();
    }

    public Color getColor() {
        return piece.getColor();
    }
}
