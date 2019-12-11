package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode
@Getter
public class CenterPoint implements Point {
    private final int position;
    private final Point leftNextPoint;
    private final Point rightNextPoint;
    private final Point previousPoint;

    public CenterPoint(int position, Point leftNextPoint, Point rightNextPoint, Point previousPoint) {
        this.position = position;
        this.leftNextPoint = leftNextPoint;
        this.rightNextPoint = rightNextPoint;
        this.previousPoint = previousPoint;
    }

    @Override
    public Point findNextDestination(Route route, int moving) {
        if (route.hasRightDiagonal()) {
            return leftNextPoint;
        }
        return rightNextPoint;
    }
}
