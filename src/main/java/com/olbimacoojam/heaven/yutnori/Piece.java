package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.point.Points;
import com.olbimacoojam.heaven.yutnori.yut.Yut;

public class Piece {
    private final Color color;
    private Point point;

    public Piece(Color color, Point point) {
        this.color = color;
        this.point = point;
    }


    public MoveResult move(Yut yut) {
        Route route = new Route();
        point.findRoute(route, yut);
        point = route.getDestination();
        return new MoveResult(this, route);
    }

    public MoveResult goBackToStart() {
        Route route = new Route();
        route.add(this.point);
        route.add(Points.get(PointName.STANDBY));
        this.point = route.getDestination();
        return new MoveResult(this, route);
    }

    public Point getPoint() {
        return point;
    }


    public boolean canMove(Point request) {
        return request == this.point;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isCaught(Color currentTeamColor, Point destination) {
        return (currentTeamColor != this.color) && (this.point == destination);
    }

}
