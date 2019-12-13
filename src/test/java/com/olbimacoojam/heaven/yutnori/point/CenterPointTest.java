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
        Point point27 = new NormalPoint(PointName.SOKYUT);
        Point point21 = new NormalPoint(PointName.BANGSUGI);
        Point point29 = new CenterPoint(PointName.BANG);
        Point point24 = new CenterRightDiagonalPoint(PointName.MOGAE);

        point29.makeConnection(point24, point21);
        point29.addInflectPoint(point27);
        point24.makeConnection(null, point29);

        Route route = new Route();
        point24.findRoute(route, 2);

        Route expectedRoute = new Route(Arrays.asList(point24, point29, point27));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint2() {
        Point noGoPoint = new NormalPoint(PointName.SOKYUT);
        Point destination = new NormalPoint(PointName.BANGSUGI);
        Point passingPoint1 = new CenterPoint(PointName.BANG);
        Point origin = new NormalPoint(PointName.DUITMOGAE);

        noGoPoint.makeConnection(passingPoint1, null);
        origin.makeConnection(null, passingPoint1);
        passingPoint1.makeConnection(origin, destination);
        passingPoint1.addInflectPoint(noGoPoint);

        Route route = new Route();
        origin.findRoute(route, 2);

        Route expectedRoute = new Route(Arrays.asList(origin, passingPoint1, destination));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("moving에 따른 다음 도착지 point 가져오기")
    void findNextPoint3() {
        Point noGoPoint = new NormalPoint(PointName.SOKYUT);
        Point destination = new NormalPoint(PointName.ANZZI);
        Point passingPoint = new NormalPoint(PointName.BANGSUGI);
        Point origin = new CenterPoint(PointName.BANG);

        origin.makeConnection(null, passingPoint);
        origin.addInflectPoint(noGoPoint);
        passingPoint.makeConnection(origin, destination);
        destination.makeConnection(passingPoint, null);
        Route route = new Route();
        origin.findRoute(route, 2);

        Route expectedRoute = new Route(Arrays.asList(origin, passingPoint, destination));
        assertThat(route).isEqualTo(expectedRoute);
    }
}