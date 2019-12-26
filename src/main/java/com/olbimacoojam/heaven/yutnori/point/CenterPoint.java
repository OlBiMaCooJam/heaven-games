package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class CenterPoint implements Point {

    private final PointName pointName;
    private Point leftNextPoint;
    private Point rightNextPoint;
    private Point previousPoint;

    CenterPoint(PointName pointName) {
        this.pointName = pointName;
    }

    @Override
    public Point findNextDestination(Route route, Yut yut) {
        if (route.hasRightDiagonal()) {
            return leftNextPoint;
        }
        return rightNextPoint;
    }

    @Override
    public void makeConnection(Point previousPoint, Point nextPoint) {
        this.previousPoint = previousPoint;
        this.rightNextPoint = nextPoint;
    }

    @Override
    public Point getNextPoint() {
        return rightNextPoint;
    }

    @Override
    public void addInflectPoint(Point inflectPoint) {
        this.leftNextPoint = inflectPoint;
    }

    @Override
    public Point getInflectPoint() {
        return leftNextPoint;
    }

}