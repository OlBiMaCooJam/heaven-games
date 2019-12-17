package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Click {
    private ClickType clickType;
    private Position position;

    public Click(ClickType clickType, Position position) {
        this.clickType = clickType;
        this.position = position;
    }
}
