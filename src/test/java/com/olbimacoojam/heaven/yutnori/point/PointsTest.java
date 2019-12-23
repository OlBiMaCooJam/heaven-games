package com.olbimacoojam.heaven.yutnori.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.olbimacoojam.heaven.yutnori.point.PointName.*;
import static org.assertj.core.api.Assertions.assertThat;

class PointsTest {
    @Test
    @DisplayName("Points 초기화 테스트")
    void initilizaePoints() {
        assertThat(Points.getTotalPointQuantities()).isEqualTo(31);
    }

    @Test
    @DisplayName("Points 순회테스트")
    void circulatePoints() {
        Point origin = Points.get(DO);
        Point destination = callGetNextTime(origin, 19);
        assertThat(destination.getPointName()).isEqualTo(PointName.CHAMMUGI);
    }

    @Test
    @DisplayName("Points 순회테스트 대각선일 때")
    void diagonalPoints() {
        Point origin = Points.get(DUITMO);
        Point inflectPoint = origin.getInflectPoint();
        Point destination = callGetNextTime(inflectPoint, 5);
        assertThat(destination.getPointName()).isEqualTo(CHAMMUGI);
    }

    @Test
    @DisplayName("제대로된 Connection 만들어졌는지 확인")
    void isBangProperlyMade() {
        Point bang = Points.get(BANG);
        assertThat(bang.getPreviousPoint().getPointName()).isEqualTo(MOGAE);
        assertThat(bang.getNextPoint().getPointName()).isEqualTo(BANGSUGI);
        assertThat(bang.getInflectPoint().getPointName()).isEqualTo(SOKYUT);
    }

    private Point callGetNextTime(Point origin, int unit) {
        int count = 0;
        while (count < unit) {
            origin = origin.getNextPoint();
            count++;
        }
        return origin;
    }
}