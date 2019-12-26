package com.olbimacoojam.heaven.draw.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LotsTest {

    @Test
    @DisplayName("사람 수, 폭탄 개수 맞게 생성")
    void of() {
        int personCount = 10;
        int whackCount = 3;
        Lots of = Lots.of(personCount, whackCount);

        List<Lot> lots = of.getLots();

        assertThat(lots.stream()
                .filter(Lot.PASS::equals)
                .count()).isEqualTo(7L);

        assertThat(lots.stream()
                .filter(Lot.WHACK::equals)
                .count()).isEqualTo(3L);
    }

    @Test
    @DisplayName("초기화 후 사람 수, 폭탄 개수 확인")
    void initialize() {
        Lots lots = new Lots();
        lots.initialize();

        List<Lot> initialLots = lots.getLots();

        assertThat(initialLots.stream()
                .filter(Lot.PASS::equals)
                .count()).isEqualTo(3L);

        assertThat(initialLots.stream()
                .filter(Lot.WHACK::equals)
                .count()).isEqualTo(1L);
    }
}