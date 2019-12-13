package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.EdgePoint;
import com.olbimacoojam.heaven.yutnori.point.NormalPoint;
import com.olbimacoojam.heaven.yutnori.point.Point;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void move_test() {
        Board board = new Board();
        Point destination = new EdgePoint(PointName.MO);
        Point passingPoint = new NormalPoint(PointName.YUT);
        Point passingPoint1 = new NormalPoint(PointName.GUL);
        Point origin = new NormalPoint(PointName.GAE);

        origin.makeConnection(null, passingPoint1);
        passingPoint1.makeConnection(passingPoint1, passingPoint);
        passingPoint.makeConnection(passingPoint1, destination);
        destination.makeConnection(passingPoint, null);

        Route route = board.findRoute(origin, 3);
        assertThat(route.getRoute()).isEqualTo(Arrays.asList(origin, passingPoint1, passingPoint, destination));
    }

    @Test
    void move_test2() {
        Board board = new Board();
        Point destination = new NormalPoint(PointName.MO);
        Point origin = new EdgePoint(PointName.MODO);

        origin.makeConnection(null, null);
        origin.addInflectPoint(destination);
        destination.makeConnection(origin, null);

        Route route = board.findRoute(origin, 1);
        assertThat(route.getRoute()).isEqualTo(Arrays.asList(origin, destination));
    }

    @Test
    void move_test3() {
        Board board = new Board();
        Point destination = new EdgePoint(PointName.MO);
        Point origin = new NormalPoint(PointName.MODO);

        origin.makeConnection(destination, null);
        destination.makeConnection(null, null);
        Route route = board.findRoute(origin, -1);

        assertThat(route.getRoute()).isEqualTo(Arrays.asList(origin, destination));
    }
}