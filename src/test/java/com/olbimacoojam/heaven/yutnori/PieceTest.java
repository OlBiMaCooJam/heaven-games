package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.point.Points;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {
    @Test
    @DisplayName("말 움직임 테스트 (대기에서 => 도)")
    void move_test() {
        Piece piece = new Piece(Color.BLACK, Points.get(PointName.STANDBY));
        MoveResult moveResult = piece.move(Yut.DO);

        Route checkRoute = new Route(Arrays.asList(Points.getStandByPoint(), Points.get(PointName.DO)));
        MoveResult checkMoveResult = new MoveResult(piece, checkRoute);
        assertThat(moveResult).isEqualTo(checkMoveResult);
        assertThat(piece.getPoint()).isEqualTo(Points.get(PointName.DO));
    }

    @Test
    @DisplayName("말 움직임 테스트 (대기에서 => 모)")
    void move_test2() {
        Points.initializePoints();
        Piece piece = new Piece(Color.BLACK, Points.get(PointName.STANDBY));
        MoveResult moveResult = piece.move(Yut.MO);

        Route checkRoute = new Route(Arrays.asList(Points.getStandByPoint(), Points.get(PointName.DO), Points.get(PointName.GAE), Points.get(PointName.GUL), Points.get(PointName.YUT), Points.get(PointName.MO)));
        MoveResult checkMoveResult = new MoveResult(piece, checkRoute);
        assertThat(moveResult).isEqualTo(checkMoveResult);
        assertThat(piece.getPoint()).isEqualTo(Points.get(PointName.MO));
    }

    @Test
    @DisplayName("말 움직임 테스트 (대기에서 => 모)")
    void move_test3() {
        Piece piece = new Piece(Color.BLACK, Points.get(PointName.MO));
        MoveResult moveResult = piece.move(Yut.GAE );

        Route checkRoute = new Route(Arrays.asList(Points.get(PointName.MO), Points.get(PointName.MODO), Points.get(PointName.MOGAE)));
        MoveResult checkMoveResult = new MoveResult(piece, checkRoute);
        assertThat(moveResult).isEqualTo(checkMoveResult);
        assertThat(piece.getPoint()).isEqualTo(Points.get(PointName.MOGAE));
    }
}