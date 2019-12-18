package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
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
        origin.findRoute(route, Yut.DO);

        Route expectedRoute = new Route(Arrays.asList(origin, destination));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("빽도일때 뒤로가기 테스트")
    void findPreviousPoint() {
        Point origin = new NormalPoint(PointName.GAE);
        Point destination = new NormalPoint(PointName.DO);
        Point noGoPoint = new NormalPoint(PointName.GUL);

        origin.makeConnection(destination, noGoPoint);
        destination.makeConnection(null, origin);
        noGoPoint.makeConnection(origin, null);

        Route route = new Route();
        origin.findRoute(route, Yut.BACKDO);
        Route expectedRoute = new Route(Arrays.asList(origin, destination));
        assertThat(route).isEqualTo(expectedRoute);
    }
}