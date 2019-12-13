package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;
import com.olbimacoojam.heaven.yutnori.yut.Yut;

public class Board {
    public Route findRoute(Point source, Yut yut) {
        Route route = new Route();
        source.findRoute(route, yut);
        return route;
    }
}