package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnTest {
    @Test
    void is_right_turn() {
        User user = new User(1L, "kjm", "olaff");
        User other = new User(2L, "other", "other");
        YutnoriParticipant yutnoriParticipant = new YutnoriParticipant(user, Color.BLACK);

        Turn turn = new Turn(yutnoriParticipant, Yut.NOT_THROWN);

        assertThat(turn.isRightTurn(user)).isTrue();
        assertThat(turn.isRightTurn(other)).isFalse();
    }

    @Test
    void process_one_throw() {
        User user = new User(1L, "kjm", "olaff");
        YutnoriParticipant yutnoriParticipant = new YutnoriParticipant(user, Color.BLACK);

        Turn turn = new Turn(yutnoriParticipant, Yut.NOT_THROWN);

        assertThat(turn.processOneThrow(Yut.DO)).isEqualTo(new Turn(yutnoriParticipant, Yut.DO));
    }

}