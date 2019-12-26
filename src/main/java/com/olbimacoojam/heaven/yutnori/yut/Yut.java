package com.olbimacoojam.heaven.yutnori.yut;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;

import java.util.Arrays;
import java.util.List;

public enum Yut {

    DO(1),
    GAE(2),
    GUL(3),
    YUT(4),
    MO(5),
    BACKDO(-1);

    private final int moveUnit;

    Yut(int moveUnit) {
        this.moveUnit = moveUnit;
    }

    public static Yut get(String pointName) {
        return valueOf(pointName);
    }

    public static List<Yut> getProbableYuts() {
        return Arrays.asList(DO, DO, DO, DO, GAE, GAE, GAE, GAE, GAE, GAE, GUL, GUL, GUL, GUL, YUT, MO, BACKDO);
    }

    public boolean isBackDo() {
        return this == BACKDO;
    }

    public boolean canMove(Route route) {
        return route.size() <= moveUnit;
    }
}
