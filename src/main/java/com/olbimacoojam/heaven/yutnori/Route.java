package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
public class Route {
    private final List<Point> route;

    public Route() {
        this.route = new ArrayList<>();
    }

    public Route(List<Point> route) {
        this.route = route;
    }

    public void add(Point point) {
        route.add(point);
    }

    public List<Point> getRoute() {
        return Collections.unmodifiableList(route);
    }

    public boolean isStartingPoint() {
        return route.size() == 1;
    }

    public boolean hasRightDiagonal() {
        return route.stream()
                .anyMatch(Point::isRightDiagonal);
    }
}
