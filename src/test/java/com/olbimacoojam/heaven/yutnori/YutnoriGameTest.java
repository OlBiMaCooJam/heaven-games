package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.board.Board;
import com.olbimacoojam.heaven.yutnori.board.OneOnOneStrategy;
import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.turn.exception.IllegalBackDoUseExeption;
import com.olbimacoojam.heaven.yutnori.turn.exception.MoveImpossibleException;
import com.olbimacoojam.heaven.yutnori.turn.exception.ThrowImpossibleException;
import com.olbimacoojam.heaven.yutnori.turn.exception.WrongUserTurnException;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class YutnoriGameTest extends YutnoriBaseTest {
    private User user1 = new User(1L, "user1", "token1");
    private User user2 = new User(2L, "user2", "token2");

    @Test
    void throw_yut_test() {
        YutnoriGame yutnoriGame = new YutnoriGame(new OneOnOneStrategy());
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        assertThat(yutnoriGame.throwYut(user1, () -> Yut.DO)).isEqualTo(Yut.DO);
        assertThrows(WrongUserTurnException.class, () -> yutnoriGame.throwYut(user2, () -> Yut.DO));
        assertThrows(ThrowImpossibleException.class, () -> yutnoriGame.throwYut(user1, () -> Yut.DO));
    }

    @Test
    void fail_throw_yut_test() {
        YutnoriGame yutnoriGame = new YutnoriGame(new OneOnOneStrategy());
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        assertThrows(WrongUserTurnException.class, () -> yutnoriGame.throwYut(user2, () -> Yut.DO));
    }

    @Test
    void move_test() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.DO);
        MoveResults moveResults = yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        MoveResults expectedMoveResults = new MoveResults(Arrays.asList(
                new MoveResult(piece, createRoute(PointName.STANDBY, PointName.DO))
        ));
        assertThat(moveResults).isEqualTo(expectedMoveResults);
    }

    @Test
    @DisplayName("게임진행 시 엎기 테스트")
    void move_test2() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
        Piece piece2 = Piece.of(Color.BLACK, PointName.DO);
        Piece piece3 = Piece.of(Color.RED, PointName.GUL);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece, piece2, piece3)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.DO);
        yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        yutnoriGame.throwYut(user2, () -> Yut.DO);
        yutnoriGame.move(user2, PointName.GUL, Yut.DO);

        yutnoriGame.throwYut(user1, () -> Yut.DO);
        MoveResults moveResults = yutnoriGame.move(user1, PointName.DO, Yut.DO);

        MoveResults expectedMoveResults = new MoveResults(Arrays.asList(
                new MoveResult(piece, createRoute(PointName.DO, PointName.GAE)),
                new MoveResult(piece2, createRoute(PointName.DO, PointName.GAE))
        ));
        assertThat(moveResults).isEqualTo(expectedMoveResults);
    }

    @Test
    @DisplayName("게임 진행시 잡기 테스트")
    void move_test3() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
        Piece piece2 = Piece.of(Color.RED, PointName.DO);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece, piece2)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.DO);
        MoveResults moveResults = yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        MoveResults expectedMoveResults = new MoveResults(Arrays.asList(
                new MoveResult(piece, createRoute(PointName.STANDBY, PointName.DO)),
                new MoveResult(piece2, createRoute(PointName.DO, PointName.STANDBY))
        ));
        assertThat(moveResults).isEqualTo(expectedMoveResults);
    }

    @Test
    @DisplayName("게임 진행시 잡기 예외1:잡았는데 움직이려 할 때")
    void move_test4() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
        Piece piece2 = Piece.of(Color.RED, PointName.DO);
        Piece piece3 = Piece.of(Color.RED, PointName.GAE);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece, piece2, piece3)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.DO);
        yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        assertThrows(MoveImpossibleException.class, () -> yutnoriGame.move(user1, PointName.DO, Yut.DO));
    }

    @Test
    @DisplayName("게임 진행시 잡기 예외2:잡았는데 다른 팀이 움직이려 할 때")
    void move_test5() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
        Piece piece2 = Piece.of(Color.RED, PointName.DO);
        Piece piece3 = Piece.of(Color.RED, PointName.GAE);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece, piece2, piece3)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.DO);
        yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        assertThrows(WrongUserTurnException.class, () -> yutnoriGame.move(user2, PointName.DO, Yut.DO));
    }

    @Test
    @DisplayName("게임 진행시 잡았을 때")
    void move_test6() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
        Piece piece2 = Piece.of(Color.RED, PointName.DO);
        Piece piece3 = Piece.of(Color.RED, PointName.GAE);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece, piece2, piece3)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.DO);
        yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        assertDoesNotThrow(() -> yutnoriGame.throwYut(user1, () -> Yut.GAE));
        assertDoesNotThrow(() -> yutnoriGame.move(user1, PointName.GAE, Yut.GAE));
    }

    @Test
    @DisplayName("모가 나온 다음 던지지 않고 말을 이동시키려 할 때")
    void move_test7() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.MO);

        assertThrows(MoveImpossibleException.class, () -> yutnoriGame.move(user1, PointName.STANDBY, Yut.MO));
    }

    @Test
    @DisplayName("모+윷+도 나온 다음 도를 사용해 이동시킨 다음 모를 사용해 움직이려 할 때")
    void move_test8() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.MO);
        yutnoriGame.throwYut(user1, () -> Yut.YUT);
        yutnoriGame.throwYut(user1, () -> Yut.DO);

        yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        assertDoesNotThrow(() -> yutnoriGame.move(user1, PointName.DO, Yut.MO));
    }

    @Test
    @DisplayName("도였을 때 빽도 나옴")
    void move_test9() {
        Piece piece = Piece.of(Color.BLACK, PointName.DO);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.BACKDO);
        MoveResults moveResults = yutnoriGame.move(user1, PointName.DO, Yut.BACKDO);

        MoveResults expectedMoveResults = new MoveResults(Arrays.asList(
                new MoveResult(piece, createRoute(PointName.DO, PointName.CHAMMUGI))
        ));

        assertThat(moveResults).isEqualTo(expectedMoveResults);
    }

    @Test
    @DisplayName("StandBy에 있을 때 빽도 나옴")
    void move_test10() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.BACKDO);
        assertDoesNotThrow(() -> yutnoriGame.move(user1, PointName.STANDBY, Yut.BACKDO));
    }

    @Test
    @DisplayName("StandBy에 있을 때 예")
    void move_test11() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.BACKDO);

        assertThrows(ThrowImpossibleException.class, () -> yutnoriGame.throwYut(user1, () -> Yut.MO));
    }

    @Test
    @DisplayName("승리했을 때 테스트")
    void move_test12() {
        Piece piece = Piece.of(Color.BLACK, PointName.CHAMMUGI);
        Piece piece2 = Piece.of(Color.RED, PointName.DO);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece, piece2)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.MO);
        yutnoriGame.throwYut(user1, () -> Yut.GAE);
        yutnoriGame.move(user1, PointName.CHAMMUGI, Yut.GAE);

        assertDoesNotThrow(() -> yutnoriGame.throwYut(user2, () -> Yut.DO));
    }

    @Test
    @DisplayName("승리했을 때 테스트, 게임 오버 테스트")
    void move_test13() {
        Piece piece = Piece.of(Color.BLACK, PointName.CHAMMUGI);
        Piece piece2 = Piece.of(Color.RED, PointName.DO);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece, piece2)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.MO);
        yutnoriGame.throwYut(user1, () -> Yut.GAE);
        yutnoriGame.move(user1, PointName.CHAMMUGI, Yut.GAE);

        assertDoesNotThrow(() -> yutnoriGame.throwYut(user2, () -> Yut.DO));
        assertThat(yutnoriGame.isGameOver().getWinners()).isEqualTo(Arrays.asList("user1"));
    }

    @Test
    @DisplayName("승리하지 않았을 때 게임 오버 테스트")
    void move_test14() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);

        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.DO);
        MoveResults moveResults = yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        assertThat(yutnoriGame.isGameOver().getWinners()).isEqualTo(Collections.emptyList());
    }

    @Test
    @DisplayName("StandBy에 있는 점 Backdo로 움직이려 할 때")
    void throw_yut_test15() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.BACKDO);
        assertDoesNotThrow(() -> yutnoriGame.move(user1, PointName.STANDBY, Yut.BACKDO));
    }

    @Test
    @DisplayName("StandBy에 모든 점이 있을 때 Backdo로 움직임")
    void throw_yut_test16() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);
        YutnoriGame yutnoriGame = new YutnoriGame(new OneOnOneStrategy());
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.BACKDO);
        assertDoesNotThrow(() -> yutnoriGame.move(user1, PointName.STANDBY, Yut.BACKDO));
    }

    @Test
    @DisplayName("모든 점이 StandBy에 없을 때 BackDo StandBy 움직임 예외처리")
    void throw_yut_test17() {
        Piece piece1 = Piece.of(Color.BLACK, PointName.STANDBY);
        Piece piece2 = Piece.of(Color.BLACK, PointName.GAE);
        Piece piece3 = Piece.of(Color.RED, PointName.STANDBY);
        YutnoriGame yutnoriGame = new YutnoriGame(yutnoriParticipants -> new Board(Arrays.asList(piece1, piece2, piece3)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1, () -> Yut.BACKDO);
        assertThrows(IllegalBackDoUseExeption.class, () -> yutnoriGame.move(user1, PointName.STANDBY, Yut.BACKDO));
    }
}