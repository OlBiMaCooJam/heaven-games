package com.olbimacoojam.heaven.yutnori.point;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class CenterRightDiagonalPoint extends NormalPoint {

    public CenterRightDiagonalPoint(PointName pointName) {
        super(pointName);
    }

    @Override
    public boolean isRightDiagonal() {
        return true;
    }
}
