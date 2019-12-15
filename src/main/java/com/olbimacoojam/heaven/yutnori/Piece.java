package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;
import com.olbimacoojam.heaven.yutnori.yut.Yut;

public class Piece {
    private final Color color;
    private Point point;

    public Piece(Color color, Point point) {
        this.color = color;
        this.point = point;
    }


    public Route move(Yut yut) {
        Route route = new Route();
        point.findRoute(route, yut);
        point = route.getDestination();
        return route;
    }

    public Point getPoint() {
        return point;
    }
}
