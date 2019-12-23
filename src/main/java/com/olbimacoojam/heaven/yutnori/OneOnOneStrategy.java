package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.point.PointName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OneOnOneStrategy implements BoardCreateStrategy {
    private static final int PIECE_QUANTITY = 4;

    @Override
    public Board createBoard(YutnoriParticipants yutnoriParticipants) {
        List<Piece> pieces = yutnoriParticipants.stream()
                .map(YutnoriParticipant::getColor)
                .map(this::createPieces)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return new Board(pieces);
    }

    private List<Piece> createPieces(Color color) {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < PIECE_QUANTITY; i++) {
            pieces.add(Piece.of(color, PointName.STANDBY));
        }
        return pieces;
    }

}
