package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import com.olbimacoojam.heaven.yutnori.point.EdgePoint;
import com.olbimacoojam.heaven.yutnori.point.NormalPoint;
import com.olbimacoojam.heaven.yutnori.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class EdgePointTest {

    @Test
    @DisplayName("moveUnit에 따른 다음 도착지 point 가져오기")
    void findNextPoint() {
        Point point6 = new NormalPoint(6, null, null);
        Point point23 = new NormalPoint(23, null, null);
        Point point5 = new EdgePoint(5, point6, null, point23);
        Point point4 = new NormalPoint(4, point5, null);
        Route route = new Route();
        point4.findRoute(route, 2);

        Route expectedRoute = new Route(Arrays.asList(point4, point5, point6));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("moveUnit에 따른 다음 도착지 point 가져오기")
    void findNextPoint2() {
        Point point6 = new NormalPoint(6, null, null);
        Point point24 = new NormalPoint(4, null, null);
        Point point23 = new NormalPoint(23, point24, null);
        Point point5 = new EdgePoint(5, point6, null, point23);
        Route route = new Route();
        point5.findRoute(route, 2);

        Route expectedRoute = new Route(Arrays.asList(point5, point23, point24));
        assertThat(route).isEqualTo(expectedRoute);
    }
}