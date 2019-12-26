package com.olbimacoojam.heaven.yutnori.yut;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;

public enum Yut {

    DO(1, 8),
    GAE(2, 12),
    GUL(3, 8),
    YUT(4, 3),
    MO(5, 2),
    BACKDO(-1, 1);

    private final int moveUnit;
    private final int weight;

    Yut(int moveUnit, int weight) {
        this.moveUnit = moveUnit;
        this.weight = weight;
    }

    public static Yut get(String pointName) {
        return valueOf(pointName);
    }

    public boolean isBackDo() {
        return this == BACKDO;
    }

    public boolean canMove(Route route) {
        return route.size() <= moveUnit;
    }

    public int getWeight() {
        return weight;
    }
}
