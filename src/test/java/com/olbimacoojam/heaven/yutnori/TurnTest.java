package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.exception.IncorrectTurnException;
import com.olbimacoojam.heaven.yutnori.exception.NotExistYutException;
import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Color;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Turn;
import com.olbimacoojam.heaven.yutnori.yutnorigame.YutnoriParticipants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TurnTest extends YutnoriBaseTest {
    private User turnUser;
    private User other;
    private Turn turn;
    private YutnoriParticipants yutnoriParticipants;

    @BeforeEach
    void setup() {
        turnUser = new User(1L, "kjm", "olaff");
        other = new User(2L, "other", "other");
        yutnoriParticipants = new YutnoriParticipants(Arrays.asList(turnUser, other));
        turn = new Turn(yutnoriParticipants.getFirst());
    }

    @Test
    void save_one_throw() {
        assertThat(turn.saveOneThrow(turnUser, Yut.MO)).isEqualTo(Yut.MO);
        assertThat(turn.saveOneThrow(turnUser, Yut.DO)).isEqualTo(Yut.DO);
        assertThrows(IncorrectTurnException.class, () -> turn.saveOneThrow(turnUser, Yut.DO));
    }

    @Test
    void fail_save_one_throw() {
        assertThrows(IncorrectTurnException.class, () -> turn.saveOneThrow(other, Yut.DO));
    }

    @Test
    void consume_yut_test() {
        turn.saveOneThrow(turnUser, Yut.DO);

        assertDoesNotThrow(() -> turn.consumeYut(turnUser, Yut.DO));
    }

    @Test
    void cosnume_yut_test2() {
        turn.saveOneThrow(turnUser, Yut.DO);

        assertThrows(NotExistYutException.class, () -> turn.consumeYut(turnUser, Yut.GAE));
    }

    @Test
    void consume_yut_test3() {
        turn.saveOneThrow(turnUser, Yut.DO);

        assertThrows(NotExistYutException.class, () -> turn.consumeYut(other, Yut.DO));
    }

    @Test
    void consume_yut_test4() {
        turn.saveOneThrow(turnUser, Yut.MO);

        assertThrows(NotExistYutException.class, () -> turn.consumeYut(other, Yut.MO));
    }

    @Test
    void consume_yut_test5() {
        turn.saveOneThrow(turnUser, Yut.MO);
        turn.saveOneThrow(turnUser, Yut.YUT);
        turn.saveOneThrow(turnUser, Yut.DO);

        assertDoesNotThrow(() -> turn.consumeYut(turnUser, Yut.DO));
        assertDoesNotThrow(() -> turn.consumeYut(turnUser, Yut.MO));
    }

    @Test
    void consume_yut_test6() {
        turn.saveOneThrow(turnUser, Yut.DO);

        assertDoesNotThrow(() -> turn.consumeYut(turnUser, Yut.DO));
        assertThrows(NotExistYutException.class, () -> turn.consumeYut(turnUser, Yut.MO));
    }

    @Test
    void next_test1() {
        turn.saveOneThrow(turnUser, Yut.DO);
        turn.consumeYut(turnUser, Yut.DO);

        Route route = createRoute(PointName.DO, PointName.GAE);
        MoveResult moveResult = new MoveResult(Piece.of(Color.BLACK, PointName.DO), route);
        MoveResults moveResults = new MoveResults(Arrays.asList(moveResult));

        Turn nextTurn = turn.next(moveResults, yutnoriParticipants);

        assertThat(nextTurn.getUser()).isEqualTo(other);
        assertThat(nextTurn.canThrow()).isTrue();
    }

    @Test
    void next_test2() {
        turn.saveOneThrow(turnUser, Yut.MO);
        turn.saveOneThrow(turnUser, Yut.YUT);
        turn.saveOneThrow(turnUser, Yut.DO);
        turn.consumeYut(turnUser, Yut.DO);

        Route route = createRoute(PointName.DO, PointName.GAE);
        MoveResult moveResult = new MoveResult(Piece.of(Color.BLACK, PointName.DO), route);
        MoveResults moveResults = new MoveResults(Arrays.asList(moveResult));

        Turn nextTurn = turn.next(moveResults, yutnoriParticipants);

        assertThat(nextTurn.getUser()).isEqualTo(turnUser);
        assertThat(nextTurn.canThrow()).isFalse();
    }

    @Test
    void next_test3() {
        turn.saveOneThrow(turnUser, Yut.MO);
        turn.saveOneThrow(turnUser, Yut.YUT);
        turn.saveOneThrow(turnUser, Yut.DO);
        turn.consumeYut(turnUser, Yut.DO);

        Route route = createRoute(PointName.DO, PointName.GAE);
        MoveResult moveResult = new MoveResult(Piece.of(Color.BLACK, PointName.GAE), route);
        Route caughtRoute = createRoute(PointName.GAE, PointName.STANDBY);
        MoveResult moveResult2 = new MoveResult(Piece.of(Color.RED, PointName.STANDBY), caughtRoute);
        MoveResults moveResults = new MoveResults(Arrays.asList(moveResult, moveResult2));

        Turn nextTurn = turn.next(moveResults, yutnoriParticipants);

        assertThat(nextTurn.getUser()).isEqualTo(turnUser);
        assertThat(nextTurn.canThrow()).isTrue();
    }
}