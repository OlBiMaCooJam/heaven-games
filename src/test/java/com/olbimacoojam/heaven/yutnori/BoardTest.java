package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.EdgePoint;
import com.olbimacoojam.heaven.yutnori.point.NormalPoint;
import com.olbimacoojam.heaven.yutnori.point.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void move_test() {
        Board board = new Board();
        Point point5 = new EdgePoint(5, null, null, null);
        Point point4 = new NormalPoint(4, point5, null);
        Point point3 = new NormalPoint(3, point4, null);
        Point point2 = new NormalPoint(2, point3, null);
        Route route = board.findRoute(point2, 3);

        assertThat(route.getRoute()).isEqualTo(Arrays.asList(point2, point3, point4, point5));
    }

    @Test
    void move_test2() {
        Board board = new Board();
        Point point23 = new NormalPoint(23, null, null);
        Point point5 = new EdgePoint(5, null, null, point23);
        Route route = board.findRoute(point5, 1);

        assertThat(route.getRoute()).isEqualTo(Arrays.asList(point5, point23));
    }

    @Test
    void move_test3() {
        Board board = new Board();
        Point point5 = new EdgePoint(5, null, null, null);
        Point point23 = new NormalPoint(23, null, point5);
        Route route = board.findRoute(point23, -1);

        assertThat(route.getRoute()).isEqualTo(Arrays.asList(point23, point5));
    }
}