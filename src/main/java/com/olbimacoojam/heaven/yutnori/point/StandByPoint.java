package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import lombok.Getter;

@Getter
public class StandByPoint implements Point {
    private final PointName pointName;
    private Point nextPoint;

    public StandByPoint(PointName pointName) {
        this.pointName = pointName;
    }

    @Override
    public Point findNextDestination(Route route, Yut yut) {
        return nextPoint;
    }

    @Override
    public Point getPreviousPoint() {
        return this;
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public void makeConnection(Point previousPoint, Point nextPoint) {
        this.nextPoint = nextPoint;
    }

    @Override
    public boolean isStandByPoint() {
        return true;
    }
}
