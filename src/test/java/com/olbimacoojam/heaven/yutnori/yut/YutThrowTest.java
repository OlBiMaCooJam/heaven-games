package com.olbimacoojam.heaven.yutnori.yut;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class YutThrowTest {
    @Test
    @DisplayName("윷던지기 테스트")
    void yut_throw_strategy() {
        YutThrowStrategy simpleYutThrowStrategy = () -> Yut.DO;
        YutThrow yutThrow = new YutThrow();
        assertThat(yutThrow.throwYut(simpleYutThrowStrategy)).isEqualTo(Yut.DO);
    }

}