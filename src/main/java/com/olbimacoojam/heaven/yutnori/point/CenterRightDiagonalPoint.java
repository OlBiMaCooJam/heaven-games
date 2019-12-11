package com.olbimacoojam.heaven.yutnori.point;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class CenterRightDiagonalPoint extends NormalPoint {

    public CenterRightDiagonalPoint(int position, Point nextPoint, Point previousPoint) {
        super(position, nextPoint, previousPoint);
    }

    @Override
    public boolean isRightDiagonal() {
        return true;
    }
}
