package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import com.olbimacoojam.heaven.yutnori.yut.Yut;

public class NormalPoint implements Point {
    private PointName pointName;
    private Point nextPoint;
    private Point previousPoint;

    public NormalPoint(PointName pointName) {
        this.pointName = pointName;
    }

    @Override
    public PointName getPointName() {
        return pointName;
    }

    @Override
    public Point getNextPoint() {
        return nextPoint;
    }

    @Override
    public Point getPreviousPoint() {
        return previousPoint;
    }

    public Point findNextDestination(Route route, Yut yut) {
        return nextPoint;
    }

    @Override
    public int getPosition() {
        return 0;
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
                ", nextPoint=" + nextPoint.getPosition() +
                '}';
    }
}
