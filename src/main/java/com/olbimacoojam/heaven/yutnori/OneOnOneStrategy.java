package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.point.PointName;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OneOnOneStrategy implements BoardCreateStrategy {

    private static final int PIECE_QUANTITY = 4;

    @Override
    public Board createBoard(YutnoriParticipants yutnoriParticipants) {
        return yutnoriParticipants.getColors().stream()
                .map(this::createPieces)
                .flatMap(Collection::stream)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Board::new));
    }

    private List<Piece> createPieces(Color color) {
        return IntStream.range(0, PIECE_QUANTITY)
                .mapToObj(i -> Piece.of(color, PointName.STANDBY))
                .collect(Collectors.toList());
    }

}
