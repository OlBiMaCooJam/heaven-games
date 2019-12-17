package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.point.Points;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Color;
import com.olbimacoojam.heaven.yutnori.yutnorigame.MoveVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.olbimacoojam.heaven.yutnori.point.PointName.GAE;
import static com.olbimacoojam.heaven.yutnori.point.PointName.GUL;
import static org.assertj.core.api.Assertions.assertThat;

class MoveVerifierTest {
    @Test
    @DisplayName("움직일 수 있는 말들 제대로 찾아오는지 확인")
    void find_movable_pieces() {
        Piece piece1 = Piece.of(Color.RED, GAE);
        Piece piece2 = Piece.of(Color.BLACK, GAE);
        Piece piece3 = Piece.of(Color.RED, GAE);
        Piece piece4 = Piece.of(Color.BLACK, GAE);
        Piece piece5 = Piece.of(Color.RED, GUL);
        List<Piece> pieces = Arrays.asList(piece1, piece2, piece3, piece4, piece5);
        MoveVerifier moveVerifier = new MoveVerifier(Color.RED, Points.get(GAE));

        assertThat(moveVerifier.findMovablePieces(pieces)).isEqualTo(Arrays.asList(piece1, piece3));
    }

    @Test
    @DisplayName("움직일 수 있는 말들 제대로 찾아오는지 확인")
    void find_movable_pieces2() {
        Piece piece1 = Piece.of(Color.RED, GAE);
        Piece piece2 = Piece.of(Color.BLACK, GAE);
        Piece piece3 = Piece.of(Color.RED, GAE);
        Piece piece4 = Piece.of(Color.BLACK, GAE);
        Piece piece5 = Piece.of(Color.RED, GUL);
        List<Piece> pieces = Arrays.asList(piece1, piece2, piece3, piece4, piece5);
        MoveVerifier moveVerifier = new MoveVerifier(Color.BLACK, Points.get(GAE));

        assertThat(moveVerifier.findMovablePieces(pieces)).isEqualTo(Arrays.asList(piece2, piece4));
    }

}