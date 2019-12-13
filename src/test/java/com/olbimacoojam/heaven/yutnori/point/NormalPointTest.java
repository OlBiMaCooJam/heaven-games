package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class NormalPointTest {
    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint() {
        Point destination = new NormalPoint(PointName.GAE);
        Point origin = new NormalPoint(PointName.DO);

        origin.makeConnection(null, destination);
        destination.makeConnection(origin, null);
        Route route = new Route();
        origin.findRoute(route, 1);

        Route expectedRoute = new Route(Arrays.asList(origin, destination));
        assertThat(route).isEqualTo(expectedRoute);
    }

}