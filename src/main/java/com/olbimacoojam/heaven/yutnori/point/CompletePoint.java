package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import com.olbimacoojam.heaven.yutnori.point.exception.NonPlayingPointException;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import lombok.Getter;

@Getter
public class CompletePoint implements Point {
    private final PointName pointName;

    CompletePoint(PointName pointName) {
        this.pointName = pointName;
    }

    @Override
    public Point findNextDestination(Route route, Yut yut) {
        return this;
    }

    @Override
    public Point getPreviousPoint() {
        throw new NonPlayingPointException();
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public void makeConnection(Point previousPoint, Point nextPoint) {

    }

    @Override
    public Point getNextPoint() {
        throw new UnsupportedOperationException();
    }
}
