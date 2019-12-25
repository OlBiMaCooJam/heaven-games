package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.turn.exception.NotHaveYutException;
import com.olbimacoojam.heaven.yutnori.turn.exception.ThrowImpossibleException;
import com.olbimacoojam.heaven.yutnori.turn.exception.WrongUserTurnException;
import com.olbimacoojam.heaven.yutnori.participant.YutnoriParticipants;
import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.turn.Turn;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
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
        yutnoriParticipants = YutnoriParticipants.of(Arrays.asList(turnUser, other));
        turn = new Turn(yutnoriParticipants.getFirst());
    }

    @Test
    void save_one_throw() {
        turn = turn.saveOneThrow(turnUser, Yut.MO);
        assertDoesNotThrow(() -> turn = turn.saveOneThrow(turnUser, Yut.DO));
        assertThrows(ThrowImpossibleException.class, () -> turn.saveOneThrow(turnUser, Yut.DO));
    }

    @Test
    void fail_save_one_throw() {
        assertThrows(WrongUserTurnException.class, () -> turn.saveOneThrow(other, Yut.DO));
    }

    @Test
    void can_move_test1() {
        turn = turn.saveOneThrow(turnUser, Yut.DO);

        assertDoesNotThrow(() -> turn.checkMove(turnUser, Yut.DO));
    }

    @Test
    void can_move_test2() {
        turn = turn.saveOneThrow(turnUser, Yut.DO);

        assertThrows(NotHaveYutException.class, () -> turn.checkMove(turnUser, Yut.GAE));
    }

    @Test
    void can_move_test3() {
        turn = turn.saveOneThrow(turnUser, Yut.DO);

        assertThrows(WrongUserTurnException.class, () -> turn.checkMove(other, Yut.DO));
    }

    @Test
    void can_move_test4() {
        turn = turn.saveOneThrow(turnUser, Yut.MO);

        assertThrows(WrongUserTurnException.class, () -> turn.checkMove(other, Yut.MO));
    }

    @Test
    void can_move_test5() {
        turn = turn.saveOneThrow(turnUser, Yut.MO);
        turn = turn.saveOneThrow(turnUser, Yut.YUT);
        turn = turn.saveOneThrow(turnUser, Yut.DO);

        assertDoesNotThrow(() -> turn.checkMove(turnUser, Yut.YUT));
    }

    @Test
    void next_test1() {
        Route route = createRoute(PointName.DO, PointName.GAE);
        MoveResult moveResult = new MoveResult(Piece.of(Color.BLACK, PointName.DO), route);
        MoveResults moveResults = new MoveResults(Arrays.asList(moveResult));

        Turn nextTurn = turn.next(moveResults, yutnoriParticipants);

        assertThat(nextTurn.getUser()).isEqualTo(other);
        assertDoesNotThrow(() -> nextTurn.saveOneThrow(other, Yut.DO));
    }

    @Test
    void next_test2() {
        turn = turn.saveOneThrow(turnUser, Yut.MO);
        turn = turn.saveOneThrow(turnUser, Yut.YUT);
        turn = turn.saveOneThrow(turnUser, Yut.DO);
        turn.removeYut(Yut.DO);

        Route route = createRoute(PointName.DO, PointName.GAE);
        MoveResult moveResult = new MoveResult(Piece.of(Color.BLACK, PointName.DO), route);
        MoveResults moveResults = new MoveResults(Arrays.asList(moveResult));

        Turn nextTurn = turn.next(moveResults, yutnoriParticipants);

        assertThat(nextTurn.getUser()).isEqualTo(turnUser);
    }

    @Test
    void next_test3() {
        turn.saveOneThrow(turnUser, Yut.DO);
        turn.removeYut(Yut.DO);

        Route route = createRoute(PointName.DO, PointName.GAE);
        MoveResult moveResult = new MoveResult(Piece.of(Color.BLACK, PointName.GAE), route);
        Route caughtRoute = createRoute(PointName.GAE, PointName.STANDBY);
        MoveResult caughtMoveResult = new MoveResult(Piece.of(Color.RED, PointName.STANDBY), caughtRoute);
        MoveResults moveResults = new MoveResults(Arrays.asList(moveResult, caughtMoveResult));

        Turn nextTurn = turn.next(moveResults, yutnoriParticipants);

        assertThat(nextTurn.getUser()).isEqualTo(turnUser);
    }
}