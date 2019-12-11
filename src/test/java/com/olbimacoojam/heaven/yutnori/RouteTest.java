package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.CenterRightDiagonalPoint;
import com.olbimacoojam.heaven.yutnori.point.NormalPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteTest {
    @Test
    void isStartingPointTest() {
        Route route = new Route();
        route.add(new NormalPoint(1, null, null));

        assertTrue(route.isStartingPoint());

        route.add(new NormalPoint(2, null, null));

        assertFalse(route.isStartingPoint());
    }

    @Test
    void hasRightDiagonalTest() {
        Route route = new Route();
        route.add(new NormalPoint(1, null, null));

        assertFalse(route.hasRightDiagonal());

        route.add(new CenterRightDiagonalPoint(2, null, null));

        assertTrue(route.hasRightDiagonal());
    }
}