package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;

public class Board {
    public Route findRoute(Point source, int moveUnit) {
        Route route = new Route();
        source.findRoute(route, moveUnit);
        return route;
    }
}