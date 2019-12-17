package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.exception.IncorrectTurnException;
import com.olbimacoojam.heaven.yutnori.piece.Piece;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.point.Points;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Board;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Color;
import com.olbimacoojam.heaven.yutnori.yutnorigame.OneOnOneStrategy;
import com.olbimacoojam.heaven.yutnori.yutnorigame.YutnoriGame;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class YutnoriGameTest {
    private User user1 = new User(1L, "user1", "token1");
    private User user2 = new User(2L, "user2", "token2");

    @Test
    void throw_yut_test() {
        YutnoriGame yutnoriGame = new YutnoriGame(() -> Yut.DO, new OneOnOneStrategy());
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        assertThat(yutnoriGame.throwYut(user1)).isEqualTo(Yut.DO);
        assertThrows(IncorrectTurnException.class, () -> yutnoriGame.throwYut(user2));
        assertThrows(IncorrectTurnException.class, () -> yutnoriGame.throwYut(user1));
    }

    @Test
    void fail_throw_yut_test() {
        YutnoriGame yutnoriGame = new YutnoriGame(() -> Yut.DO, new OneOnOneStrategy());
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        assertThrows(IncorrectTurnException.class, () -> yutnoriGame.throwYut(user2));
    }

    @Test
    void move_test() {
        Piece piece = Piece.of(Color.BLACK, PointName.STANDBY);

        YutnoriGame yutnoriGame = new YutnoriGame(() -> Yut.DO, yutnoriParticipants -> new Board(Arrays.asList(piece)));
        yutnoriGame.initialize(Arrays.asList(user1, user2));

        yutnoriGame.throwYut(user1);
        MoveResults moveResults = yutnoriGame.move(user1, PointName.STANDBY, Yut.DO);

        MoveResults expectedMoveResults = new MoveResults(Arrays.asList(
                new MoveResult(piece, createRoute(PointName.STANDBY, PointName.DO))
        ));
        assertThat(moveResults).isEqualTo(expectedMoveResults);
    }

    private Route createRoute(PointName... pointNames) {
        Route route = new Route();

        for (PointName pointName : pointNames) {
            route.add(Points.get(pointName));
        }

        return route;
    }
}