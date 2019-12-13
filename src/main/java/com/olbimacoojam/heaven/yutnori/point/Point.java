package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;

public interface Point {

    default void findRoute(Route route, int moveUnit) {
        route.add(this);

        if (moveUnit == -1) {
            route.add(getPreviousPoint());
        }

        if (moveUnit > 0) {
            Point nextPoint = findNextDestination(route, moveUnit);
            nextPoint.findRoute(route, --moveUnit);
        }
    }

    default void addInflectPoint(Point inflectionPoint) {
        throw new UnsupportedOperationException();
    }

    Point findNextDestination(Route route, int moving);

    Point getPreviousPoint();

    PointName getPointName();

    int getPosition();

    default boolean isRightDiagonal() {
        return false;
    }

    void makeConnection(Point previousPoint, Point nextPoint);

    Point getNextPoint();

    default Point getInflectPoint() {
        throw new UnsupportedOperationException();
    }

    ;
}

