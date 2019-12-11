package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class EdgePoint implements Point {
    private final int position;
    private final Point nextPoint;
    private final Point previousPoint;
    private final Point inflectPoint;

    public EdgePoint(int position, Point nextPoint, Point previousPoint, Point inflectPoint) {
        this.position = position;
        this.nextPoint = nextPoint;
        this.previousPoint = previousPoint;
        this.inflectPoint = inflectPoint;
    }

    @Override
    public Point findNextDestination(Route route, int moving) {
        if (route.isStartingPoint()) {
            return inflectPoint;
        }
        return nextPoint;
    }

    @Override
    public String toString() {
        return "EdgePoint{" +
                "position=" + position +
                ", nextPoint=" + nextPoint.getPosition() +
                ", inflectPoint=" + inflectPoint.getPosition() +
                '}';
    }
}
