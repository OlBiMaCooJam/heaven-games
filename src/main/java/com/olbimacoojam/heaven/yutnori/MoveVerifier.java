package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.exception.NoAvailablePieceException;
import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.point.Point;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.point.Points;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class MoveVerifier {

    private final Color color;
    private final Point point;

    public MoveVerifier(Color color, Point point) {
        this.color = color;
        this.point = point;
    }

    public static MoveVerifier of(Color color, PointName pointName) {
        return new MoveVerifier(color, Points.get(pointName));
    }

    public List<Piece> findMovablePieces(List<Piece> pieces) {
        List<Piece> movablePieces = getMovablePieces(pieces);
        checkWrongMove(movablePieces);

        if (point.isStandByPoint()) {
            return Collections.singletonList(movablePieces.get(0));
        }
        return movablePieces;
    }

    private List<Piece> getMovablePieces(List<Piece> pieces) {
        return pieces.stream()
                .filter(this::isMovable)
                .collect(Collectors.toList());
    }

    private boolean isMovable(Piece piece) {
        return piece.isMovable(color, point);
    }

    private void checkWrongMove(List<Piece> movablePieces) {
        checkCanMove(movablePieces);
    }

    private void checkCanMove(List<Piece> movablePieces) {
        if (movablePieces.size() == 0) {
            throw new NoAvailablePieceException(point.getPointName());
        }
    }
}
