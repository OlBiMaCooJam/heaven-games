package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class CenterPointTest {

    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint() {
        Point point27 = new NormalPoint(27, null, null);
        Point point21 = new NormalPoint(21, null, null);
        Point point29 = new CenterPoint(29, point27, point21, null);
        Point point24 = new CenterRightDiagonalPoint(24, point29, null);
        Route route = new Route();
        point24.findRoute(route, 2);

        Route expectedRoute = new Route(Arrays.asList(point24, point29, point27));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint2() {
        Point point27 = new NormalPoint(27, null, null);
        Point point21 = new NormalPoint(21, null, null);
        Point point29 = new CenterPoint(6, point27, point21, null);
        Point point26 = new NormalPoint(26, point29, null);
        Route route = new Route();
        point26.findRoute(route, 2);

        Route expectedRoute = new Route(Arrays.asList(point26, point29, point21));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint3() {
        Point point27 = new NormalPoint(27, null, null);
        Point point22 = new NormalPoint(22, null, null);
        Point point21 = new NormalPoint(21, point22, null);
        Point point29 = new CenterPoint(6, point27, point21, null);
        Route route = new Route();
        point29.findRoute(route, 2);

        Route expectedRoute = new Route(Arrays.asList(point29, point21, point22));
        assertThat(route).isEqualTo(expectedRoute);
    }
}