package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.point.Points;
import org.junit.jupiter.api.Test;

import static com.olbimacoojam.heaven.yutnori.point.PointName.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteTest {
    @Test
    void isStartingPointTest() {
        Route route = new Route();
        route.add(Points.get(DO));

        assertTrue(route.isStartingPoint());

        route.add(Points.get(GAE));

        assertFalse(route.isStartingPoint());
    }

    @Test
    void hasRightDiagonalTest() {
        Route route = new Route();
        route.add(Points.get(DO));

        assertFalse(route.hasRightDiagonal());

        route.add(Points.get(MOGAE));
        assertTrue(route.hasRightDiagonal());
    }
}