package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClickTest {
    @Test
    void leftClickOn() {
        Click click = Click.leftClickOn(Position.of(1, 2));
        assertThat(click.getClickType().isLeftClick()).isTrue();
    }
}