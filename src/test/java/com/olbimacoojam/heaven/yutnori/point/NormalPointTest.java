package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import com.olbimacoojam.heaven.yutnori.point.NormalPoint;
import com.olbimacoojam.heaven.yutnori.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NormalPointTest {
    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint() {
        Point point2 = new NormalPoint(2, null, null);
        Point point1 = new NormalPoint(1, point2, null);
        Route route = new Route();
        point1.findRoute(route, 1);

        Route expectedRoute = new Route(Arrays.asList(point1, point2));
        assertThat(route).isEqualTo(expectedRoute);
    }

}