package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.point.Points;

public abstract class YutnoriBaseTest {
    protected Route createRoute(PointName... pointNames) {
        Route route = new Route();

        for (PointName pointName : pointNames) {
            route.add(Points.get(pointName));
        }

        return route;
    }
}
