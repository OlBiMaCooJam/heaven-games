package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.olbimacoojam.heaven.yutnori.point.PointName.BANGSUGI;
import static org.assertj.core.api.Assertions.assertThat;

class CenterPointTest {

    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint() {
        Point destination = new NormalPoint(PointName.SOKYUT);
        Point noGoPoint = new NormalPoint(BANGSUGI);
        Point passingPoint = new CenterPoint(PointName.BANG);
        Point origin = new CenterRightDiagonalPoint(PointName.MOGAE);

        passingPoint.makeConnection(origin, noGoPoint);
        passingPoint.addInflectPoint(destination);
        origin.makeConnection(null, passingPoint);

        Route route = new Route();
        origin.findRoute(route, Yut.GAE);

        Route expectedRoute = new Route(Arrays.asList(PointName.MOGAE, PointName.BANG, PointName.SOKYUT));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint2() {
        Point noGoPoint = new NormalPoint(PointName.SOKYUT);
        Point destination = new NormalPoint(BANGSUGI);
        Point passingPoint = new CenterPoint(PointName.BANG);
        Point origin = new NormalPoint(PointName.DUITMOGAE);

        noGoPoint.makeConnection(passingPoint, null);
        origin.makeConnection(null, passingPoint);
        passingPoint.makeConnection(origin, destination);
        passingPoint.addInflectPoint(noGoPoint);

        Route route = new Route();
        origin.findRoute(route, Yut.GAE);

        Route expectedRoute = new Route(Arrays.asList(PointName.DUITMOGAE, PointName.BANG, BANGSUGI));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint3() {
        Point noGoPoint = new NormalPoint(PointName.SOKYUT);
        Point destination = new NormalPoint(PointName.ANZZI);
        Point passingPoint = new NormalPoint(BANGSUGI);
        Point origin = new CenterPoint(PointName.BANG);

        origin.makeConnection(null, passingPoint);
        origin.addInflectPoint(noGoPoint);
        passingPoint.makeConnection(origin, destination);
        destination.makeConnection(passingPoint, null);
        Route route = new Route();
        origin.findRoute(route, Yut.GAE);

        Route expectedRoute = new Route(Arrays.asList(PointName.BANG, PointName.BANGSUGI, PointName.ANZZI));
        assertThat(route).isEqualTo(expectedRoute);
    }
}