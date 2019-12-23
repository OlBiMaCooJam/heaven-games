package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.api.Test;

class ClickTest {
    @Test
    void leftClickOn() {
        Click click = Click.leftClickOn(Position.of(1, 2));

    }
}