package com.olbimacoojam.heaven.yutnori.piece;

import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Color;
import com.olbimacoojam.heaven.yutnori.yutnorigame.YutnoriParticipant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OneOnOneStrategy implements PieceSetStrategy {

    private final List<YutnoriParticipant> yutnoriParticipants;

    public OneOnOneStrategy(List<YutnoriParticipant> yutnoriParticipants) {
        this.yutnoriParticipants = yutnoriParticipants;
    }

    @Override
    public List<Piece> setPieces() {
        return yutnoriParticipants.stream()
                .map(YutnoriParticipant::getColor)
                .map(this::createPieces)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Piece> createPieces(Color color) {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            pieces.add(Piece.of(color, PointName.STANDBY));
        }
        return pieces;
    }
}
