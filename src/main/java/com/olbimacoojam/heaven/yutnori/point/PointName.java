package com.olbimacoojam.heaven.yutnori.point;

import java.util.function.Function;

public enum PointName {
    STANDBY(StandByPoint::new, null, "DO"),
    COMPLETE(CompletePoint::new, null, null),
    DO(NormalPoint::new, "STANDBY", "GAE"),
    GAE(NormalPoint::new, "DO", "GUL"),
    GUL(NormalPoint::new, "GAE", "YUT"),
    YUT(NormalPoint::new, "GUL", "MO"),
    MO(EdgePoint::new, "YUT", "DUITDO"),
    DUITDO(NormalPoint::new, "MO", "DUITGAE"),
    DUITGAE(NormalPoint::new, "DUITDO", "DUITGUL"),
    DUITGUL(NormalPoint::new, "DUITGAE", "DUITYUT"),
    DUITYUT(NormalPoint::new, "DUITGUL", "DUITMO"),
    DUITMO(EdgePoint::new, "DUITYUT", "ZZIDO"),
    ZZIDO(NormalPoint::new, "DUITMO", "ZZIGAE"),
    ZZIGAE(NormalPoint::new, "ZZIDO", "ZZIGUL"),
    ZZIGUL(NormalPoint::new, "ZZIGAE", "ZZIYUT"),
    ZZIYUT(NormalPoint::new, "ZZIGUL", "ZZIMO"),
    ZZIMO(NormalPoint::new, "ZZIYUT", "NALDO"),
    NALDO(NormalPoint::new, "ZZIMO", "NALGAE"),
    NALGAE(NormalPoint::new, "NALDO", "NALGUL"),
    NALGUL(NormalPoint::new, "NALGAE", "NALYUT"),
    NALYUT(NormalPoint::new, "NALGUL", "CHAMMUGI"),
    CHAMMUGI(NormalPoint::new, "NALYUT", "COMPLETE"),
    MODO(NormalPoint::new, "MO", "MOGAE"),
    MOGAE(CenterRightDiagonalPoint::new, "BANG", "MODO"),
    BANG(CenterPoint::new, "MOGAE", "BANGSUGI"),
    DUITMODO(NormalPoint::new, "DUITMO", "DUITMOGAE"),
    DUITMOGAE(NormalPoint::new, "DUITMODO", "BANG"),
    SOKYUT(NormalPoint::new, "BANG", "SOKMO"),
    SOKMO(NormalPoint::new, "SOKYUT", "ZZIMO"),
    BANGSUGI(NormalPoint::new, "BANG", "ANZZI"),
    ANZZI(NormalPoint::new, "BANGSUGI", "CHAMMUGI");

    private final Function<PointName, Point> pointGenerator;
    private final String previousPointName;
    private final String nextPointName;

    PointName(Function<PointName, Point> pointGenerator, String previousPointName, String nextPointName) {
        this.pointGenerator = pointGenerator;
        this.previousPointName = previousPointName;
        this.nextPointName = nextPointName;
    }

    public Point createPoint() {
        Point currentPoint = this.pointGenerator.apply(this);
        return currentPoint;
    }

    public PointName getPrevPointName() {
        return getPointName(previousPointName);
    }

    public PointName getNextPointName() {
        return getPointName(nextPointName);
    }

    private PointName getPointName(String pointName) {
        return pointName == null ? null : valueOf(pointName);
    }
}
