package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.CenterRightDiagonalPoint;
import com.olbimacoojam.heaven.yutnori.point.NormalPoint;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteTest {
    @Test
    void isStartingPointTest() {
        Route route = new Route();
        route.add(new NormalPoint(PointName.DO));

        assertTrue(route.isStartingPoint());

        route.add(new NormalPoint(PointName.GAE));

        assertFalse(route.isStartingPoint());
    }

    @Test
    void hasRightDiagonalTest() {
        Route route = new Route();
        route.add(new NormalPoint(PointName.DO));

        assertFalse(route.hasRightDiagonal());

        route.add(new CenterRightDiagonalPoint(PointName.MOGAE));

        assertTrue(route.hasRightDiagonal());
    }
}