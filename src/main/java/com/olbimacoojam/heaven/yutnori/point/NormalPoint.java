package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import lombok.Getter;

@Getter
public class NormalPoint implements Point {

    private final PointName pointName;
    private Point nextPoint;
    private Point previousPoint;

    NormalPoint(PointName pointName) {
        this.pointName = pointName;
    }

    public Point findNextDestination(Route route, Yut yut) {
        return nextPoint;
    }

    @Override
    public void makeConnection(Point previousPoint, Point nextPoint) {
        this.previousPoint = previousPoint;
        this.nextPoint = nextPoint;
    }

    @Override
    public String toString() {
        return "NormalPoint{" +
                "pointName=" + pointName +
                ", nextPoint=" + nextPoint.getPointName() +
                '}';
    }
}
