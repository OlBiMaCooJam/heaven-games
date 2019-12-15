package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;
import com.olbimacoojam.heaven.yutnori.yut.Yut;

import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private final List<Piece> pieces;

    public Board(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public MoveResults move(Point request, Yut yut) {
        List<Piece> movablePieces = pieces.stream()
                .filter(piece -> piece.canMove(request))
                .collect(Collectors.toList());

        List<MoveResult> moveResults = movablePieces.stream()
                .map(piece -> piece.move(yut))
                .collect(Collectors.toList());

        //잡았는지 체크
        Color currentTeamColor = movablePieces.get(0).getColor();
        Point destination = moveResults.get(0).findDestination();

        List<Piece> caughtPieces = pieces.stream()
                .filter(piece -> piece.isCaught(currentTeamColor, destination))
                .collect(Collectors.toList());

        List<MoveResult> caughtMoveResults = caughtPieces.stream()
                .map(piece -> piece.goBackToStart())
                .collect(Collectors.toList());

        moveResults.addAll(caughtMoveResults);
        return new MoveResults(moveResults);
    }
}