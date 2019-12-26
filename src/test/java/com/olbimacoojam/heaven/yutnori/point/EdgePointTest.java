package com.olbimacoojam.heaven.yutnori.point;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class EdgePointTest {

    @Test
    @DisplayName("moveUnit에 따른 다음 도착지 point 가져오기")
    void findNextPoint() {
        Point destination = new NormalPoint(PointName.DUITDO);
        Point noGoPoint = new NormalPoint(PointName.MODO);
        Point passingPoint = new EdgePoint(PointName.MO);
        Point origin = new NormalPoint(PointName.YUT);

        origin.makeConnection(null, passingPoint);
        passingPoint.makeConnection(origin, destination);
        passingPoint.addInflectPoint(noGoPoint);
        destination.makeConnection(passingPoint, null);

        Route route = new Route();
        origin.findRoute(route, Yut.GAE);

        Route expectedRoute = new Route(Arrays.asList(origin, passingPoint, destination));
        assertThat(route).isEqualTo(expectedRoute);
    }

    @Test
    @DisplayName("moveUnit에 따른 다음 도착지 point 가져오기")
    void findNextPoint2() {
        Point noGoPoint = new NormalPoint(PointName.DUITDO);
        Point destination = new CenterRightDiagonalPoint(PointName.MOGAE);
        Point passingPoint = new NormalPoint(PointName.MODO);
        Point origin = new EdgePoint(PointName.MO);

        origin.makeConnection(null, noGoPoint);
        origin.addInflectPoint(passingPoint);
        passingPoint.makeConnection(origin, destination);
        destination.makeConnection(passingPoint, null);

        Route route = new Route();
        origin.findRoute(route, Yut.GAE);

        Route expectedRoute = new Route(Arrays.asList(origin, passingPoint, destination));
        assertThat(route).isEqualTo(expectedRoute);
    }
}