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
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
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
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
        MoveResult moveResult = piece.move(Yut.MO);

        Route checkRoute = new Route(Arrays.asList(Points.getStandByPoint(), Points.get(PointName.DO), Points.get(PointName.GAE), Points.get(PointName.GUL), Points.get(PointName.YUT), Points.get(PointName.MO)));
        MoveResult checkMoveResult = new MoveResult(piece, checkRoute);
        assertThat(moveResult).isEqualTo(checkMoveResult);
        assertThat(piece.getPoint()).isEqualTo(Points.get(PointName.MO));
    }

    @Test
    @DisplayName("말 움직임 테스트 (대기에서 => 모)")
    void move_test3() {
        Piece piece = Piece.of(Color.BLACK, PointName.MO);
        MoveResult moveResult = piece.move(Yut.GAE );

        Route checkRoute = new Route(Arrays.asList(Points.get(PointName.MO), Points.get(PointName.MODO), Points.get(PointName.MOGAE)));
        MoveResult checkMoveResult = new MoveResult(piece, checkRoute);
        assertThat(moveResult).isEqualTo(checkMoveResult);
        assertThat(piece.getPoint()).isEqualTo(Points.get(PointName.MOGAE));
    }

    @Test
    @DisplayName("말 움직임 테스트 (잡혓을때)")
    void move_test4() {
        Piece piece = Piece.of(Color.BLACK, PointName.MO);
        MoveResult moveResult = piece.goBackToStandBy();

        Route checkRoute = new Route(Arrays.asList(Points.get(PointName.MO), Points.get(PointName.STANDBY)));
        MoveResult checkMoveResult = new MoveResult(piece, checkRoute);
        assertThat(moveResult).isEqualTo(checkMoveResult);
        assertThat(piece.getPoint()).isEqualTo(Points.get(PointName.STANDBY));
    }

    @Test
    @DisplayName("말 움직일 수 있는지 확인 테스트")
    void is_movable() {
        Piece piece = Piece.of(Color.RED, PointName.MO);
        assertThat(piece.isMovable(Color.RED, Points.get(PointName.MO))).isTrue();
    }

    @Test
    @DisplayName("말 움직일 수 없는지 확인 테스트")
    void is_movable2() {
        Piece piece = Piece.of(Color.RED, PointName.MO);
        assertThat(piece.isMovable(Color.BLACK, Points.get(PointName.MO))).isFalse();
    }

    @Test
    @DisplayName("말 움직일 수 없는지 확인 테스트")
    void is_movable3() {
        Piece piece = Piece.of(Color.RED, PointName.MO);
        assertThat(piece.isMovable(Color.RED, Points.get(PointName.GAE))).isFalse();
    }

    @Test
    @DisplayName("말 잡을 수 있는지 확인 테스트")
    void can_catch() {
        Piece piece = Piece.of(Color.RED, PointName.GUL);
        Piece targetPiece = Piece.of(Color.BLACK, PointName.GUL);
        assertThat(piece.canCatch(targetPiece)).isTrue();
    }

    @Test
    @DisplayName("말 잡을 수 없는지 확인 테스트")
    void can_catch2() {
        Piece piece = Piece.of(Color.RED, PointName.GUL);
        Piece targetPiece = Piece.of(Color.RED, PointName.GUL);
        assertThat(piece.canCatch(targetPiece)).isFalse();
    }

}