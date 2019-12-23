package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Click {
    private ClickType clickType;
    private Position position;

    private Click(ClickType clickType, Position position) {
        this.clickType = clickType;
        this.position = position;
    }

    public static Click leftClickOn(Position position) {
        return new Click(ClickType.LEFT, position);
    }
}
