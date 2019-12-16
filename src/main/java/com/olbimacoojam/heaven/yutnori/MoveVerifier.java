package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MoveVerifier {
    private final Color color;
    private final Point point;

    public MoveVerifier(Color color, Point point) {
        this.color = color;
        this.point = point;
    }

    public boolean isMovable(Piece piece) {
        return piece.isMovable(color, point);
    }

    public List<Piece> findMovablePieces(List<Piece> pieces) {
        List<Piece> movablePieces = pieces.stream()
                .filter(this::isMovable)
                .collect(Collectors.toList());

        if (point.isStandByPoint()) {
            return Collections.singletonList(movablePieces.get(0));
        }
        return movablePieces;
    }
}
