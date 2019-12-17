package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.exception.IncorrectTurnException;
import com.olbimacoojam.heaven.yutnori.exception.NotExistYutException;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Color;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Turn;
import com.olbimacoojam.heaven.yutnori.yutnorigame.YutnoriParticipant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TurnTest {
    private User user;
    private User other;
    private YutnoriParticipant yutnoriParticipant;
    private Turn turn;

    @BeforeEach
    void setup() {
        user = new User(1L, "kjm", "olaff");
        other = new User(2L, "other", "other");
        yutnoriParticipant = new YutnoriParticipant(user, Color.BLACK);
        turn = new Turn(yutnoriParticipant);
    }

    @Test
    void save_one_throw() {
        assertThat(turn.saveOneThrow(user, Yut.MO)).isEqualTo(Yut.MO);
        assertThat(turn.saveOneThrow(user, Yut.DO)).isEqualTo(Yut.DO);
        assertThrows(IncorrectTurnException.class, () -> turn.saveOneThrow(user, Yut.DO));
    }

    @Test
    void fail_save_one_throw() {
        assertThrows(IncorrectTurnException.class, () -> turn.saveOneThrow(other, Yut.DO));
    }

    @Test
    void consume_yut_test() {
        turn.saveOneThrow(user, Yut.DO);

        assertDoesNotThrow(() -> turn.consumeYut(user, Yut.DO));
    }

    @Test
    void cosnume_yut_test2() {
        turn.saveOneThrow(user, Yut.DO);

        assertThrows(NotExistYutException.class, () -> turn.consumeYut(user, Yut.GAE));
    }

    @Test
    void consume_yut_test3() {
        turn.saveOneThrow(user, Yut.DO);

        assertThrows(NotExistYutException.class, () -> turn.consumeYut(other, Yut.DO));
    }

    @Test
    void consume_yut_test4() {
        turn.saveOneThrow(user, Yut.MO);

        assertThrows(NotExistYutException.class, () -> turn.consumeYut(other, Yut.MO));
    }

    @Test
    void consume_yut_test5() {
        turn.saveOneThrow(user, Yut.MO);
        turn.saveOneThrow(user, Yut.YUT);
        turn.saveOneThrow(user, Yut.DO);

        assertDoesNotThrow(() -> turn.consumeYut(user, Yut.DO));
        assertDoesNotThrow(() -> turn.consumeYut(user, Yut.MO));
    }

    @Test
    void consume_yut_test6() {
        turn.saveOneThrow(user, Yut.DO);

        assertDoesNotThrow(() -> turn.consumeYut(user, Yut.DO));
        assertThrows(NotExistYutException.class, () -> turn.consumeYut(user, Yut.MO));
    }
}