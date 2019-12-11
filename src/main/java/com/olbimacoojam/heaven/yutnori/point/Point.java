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

    Point findNextDestination(Route route, int moving);

    Point getPreviousPoint();

    int getPosition();

    default boolean isRightDiagonal() {
        return false;
    }
}
