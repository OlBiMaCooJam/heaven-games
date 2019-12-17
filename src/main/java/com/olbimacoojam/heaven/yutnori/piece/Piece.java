package com.olbimacoojam.heaven.yutnori.piece;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.point.Point;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.point.Points;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yutnorigame.Color;

public class Piece {
    private final Color color;
    private Point point;

    private Piece(Color color, Point point) {
        this.color = color;
        this.point = point;
    }

    public static Piece of(Color color, PointName pointName) {
        return new Piece(color, Points.get(pointName));
    }

    public MoveResult move(Yut yut) {
        Route route = new Route();
        point.findRoute(route, yut);
        point = route.getDestination();
        return new MoveResult(this, route);
    }

    public MoveResult goBackToStandBy() {
        Route route = new Route();
        route.add(this.point);
        route.add(Points.get(PointName.STANDBY));
        this.point = route.getDestination();
        return new MoveResult(this, route);
    }

    public boolean isMovable(Color color, Point point) {
        return this.color == color && this.point == point;
    }

    public boolean canCatch(Piece piece) {
        return this.color != piece.color && this.point == piece.point;
    }

    public Point getPoint() {
        return point;
    }
}
