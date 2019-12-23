package com.olbimacoojam.heaven.yutnori.piece.moveresult;

import com.olbimacoojam.heaven.yutnori.point.Point;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.point.Points;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Route {

    private final List<PointName> route;

    public Route() {
        this.route = new ArrayList<>();
    }

    public Route(List<PointName> route) {
        this.route = route;
    }

    public void add(Point point) {
        route.add(point.getPointName());
    }

    public List<PointName> getRoute() {
        return Collections.unmodifiableList(route);
    }

    public boolean isStartingPoint() {
        return route.size() == 1;
    }

    public boolean hasRightDiagonal() {
        return route.stream()
                .map(Points::get)
                .anyMatch(Point::isRightDiagonal);
    }

    public int size() {
        return route.size();
    }

    public Point getDestination() {
        return Points.get(route.get(route.size() - 1));
    }

    public boolean isDestination(PointName pointName) {
        Point destination = getDestination();
        return destination.isName(pointName);
    }

    @Override
    public String toString() {
        String route = this.route.stream()
                .map(PointName::name)
                .collect(Collectors.joining(", "));

        return "Route{" +
                "route=" + route +
                '}';
    }
}
