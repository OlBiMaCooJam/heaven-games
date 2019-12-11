package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode
@Getter
public class NormalPoint implements Point {
    private final int position;
    private final Point nextPoint;
    private final Point previousPoint;

    public NormalPoint(int position, Point nextPoint, Point previousPoint) {
        this.position = position;
        this.nextPoint = nextPoint;
        this.previousPoint = previousPoint;
    }

    public Point findNextDestination(Route route, int moving) {
        return nextPoint;
    }

    @Override
    public String toString() {
        return "NormalPoint{" +
                "position=" + position +
                ", nextPoint=" + nextPoint.getPosition() +
                '}';
    }
}
