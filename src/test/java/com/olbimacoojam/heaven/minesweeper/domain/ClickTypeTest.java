package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClickTypeTest {
    @Test
    @DisplayName("왼쪽 클릭인지인 확인")
    void isLeftClick() {
        ClickType clickType = ClickType.LEFT;
        assertThat(clickType.isLeftClick()).isTrue();
    }

    @Test
    @DisplayName("왼쪽 클릭이 아닌지 확인")
    void isNotLeftClick() {
        ClickType clickType = ClickType.RIGHT;
        assertThat(clickType.isLeftClick()).isFalse();
    }
}