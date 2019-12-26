package com.olbimacoojam.heaven.yutnori.point;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.olbimacoojam.heaven.yutnori.point.PointName.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Points {

    private static final Map<PointName, Point> points;

    static {
        points = initializePoints();
        connectPoints();
    }

    private static Map<PointName, Point> initializePoints() {
        return Arrays.stream(PointName.values())
                .collect(Collectors.toMap(Function.identity(), PointName::createPoint));
    }

    private static void connectPoints() {
        for (PointName pointName : PointName.values()) {
            Point prevPoint = points.get(pointName.getPrevPointName());
            Point nextPoint = points.get(pointName.getNextPointName());

            Point point = points.get(pointName);
            point.makeConnection(prevPoint, nextPoint);
        }

        Map<PointName, PointName> inflectRelation = new HashMap<>();
        inflectRelation.put(MO, MODO);
        inflectRelation.put(BANG, SOKYUT);
        inflectRelation.put(DUITMO, DUITMODO);

        for (PointName pointName : inflectRelation.keySet()) {
            Point point = points.get(pointName);
            PointName destinationPointName = inflectRelation.get(pointName);
            Point destinationPoint = points.get(destinationPointName);

            point.addInflectPoint(destinationPoint);
        }
    }

    public static int getTotalPointQuantities() {
        return points.keySet().size();
    }

    public static Point get(PointName pointName) {
        return points.get(pointName);
    }

    public static boolean isCompleted(Point point) {
        return points.get(COMPLETE).equals(point);
    }
}
