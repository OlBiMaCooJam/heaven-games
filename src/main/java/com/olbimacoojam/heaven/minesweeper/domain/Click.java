package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Click {
    private ClickType clickType;
    private Position position;

    public static Click leftClickOn(Position position) {
        Click click = new Click();
        click.clickType = ClickType.LEFT;
        click.position = position;
        return click;
    }
}
