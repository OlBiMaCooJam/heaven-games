package com.olbimacoojam.heaven.yutnori.yut;

import com.olbimacoojam.heaven.yutnori.Route;

public enum Yut {
    DO(1),
    GAE(2),
    GUL(3),
    YUT(4),
    MO(5),
    BACKDO(-1),
    NOT_THROWN(0);

    private final int moveUnit;

    Yut(int moveUnit) {
        this.moveUnit = moveUnit;
    }

    public boolean isBackDo() {
        return this == BACKDO;
    }

    public boolean canMove(Route route) {
        return route.size() <= moveUnit;
    }
}
