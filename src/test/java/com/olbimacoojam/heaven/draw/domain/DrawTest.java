package com.olbimacoojam.heaven.draw.domain;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.draw.domain.exception.InvalidWhackCountException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfUsersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DrawTest {
    private Draw draw = new Draw();

    @Test
    @DisplayName("한명이 게임 시작")
    void initializeNormal() {
        List<User> users = Collections.singletonList(new User());

        assertDoesNotThrow(() -> draw.initialize(users));
    }

    @Test
    @DisplayName("한명이 게임 시작")
    void invalidInitialize() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "martin", "token"));
        users.add(new User(2L, "bmo", "token2"));

        assertThrows(InvalidNumberOfUsersException.class, () -> draw.initialize(users));
    }

    @Test
    @DisplayName("사람 수 10, 폭탄수 4")
    void startGame() {
        draw.startGame(10, 4);
        Lots lots = draw.getLots();
        List<Lot> afterLots = lots.getLots();

        assertThat(afterLots.stream()
                .filter(Lot.PASS::equals)
                .count()).isEqualTo(6L);

        assertThat(afterLots.stream()
                .filter(Lot.WHACK::equals)
                .count()).isEqualTo(4L);
    }

    @Test
    @DisplayName("폭탄 수가 사람 수보다 많은 경우")
    void invalidGameStart() {
        assertThrows(InvalidWhackCountException.class, () -> draw.startGame(4, 5));
    }
}