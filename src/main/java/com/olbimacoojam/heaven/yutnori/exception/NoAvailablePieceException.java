package com.olbimacoojam.heaven.yutnori.exception;

import com.olbimacoojam.heaven.yutnori.point.PointName;

public class NoAvailablePieceException extends RuntimeException {

    private static final String MESSAGE = "에는 움직일 수 있는 말이 없습니다.";

    public NoAvailablePieceException(PointName pointName) {
        super(pointName + MESSAGE);
    }
}
