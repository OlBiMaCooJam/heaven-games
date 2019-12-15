package com.olbimacoojam.heaven.yutnori.point;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.olbimacoojam.heaven.yutnori.point.PointName.*;

public class Points {
    private final static Point standByPoint;
    private static Map<PointName, Point> points;

    static {
        points = initializePoints();
        standByPoint = points.get(STANDBY);
    }

    public static Map<PointName, Point> initializePoints() {
        Map<PointName, Point> map = Arrays.stream(PointName.values())
                .collect(Collectors.toMap(p -> p, PointName::createPoint));

        connectPoints(map);

        return map;
    }

    private static void connectPoints(Map<PointName, Point> map) {
        for (PointName pointName : PointName.values()) {
            Point prevPoint = map.get(pointName.getPrevPointName());
            Point nextPoint = map.get(pointName.getNextPointName());

            Point point = map.get(pointName);
            point.makeConnection(prevPoint, nextPoint);
        }

        Map<PointName, PointName> inflectRelation = new HashMap<>();
        inflectRelation.put(MO, MODO);
        inflectRelation.put(BANG, SOKYUT);
        inflectRelation.put(DUITMO, DUITMODO);

        for (PointName pointName : inflectRelation.keySet()) {
            Point point = map.get(pointName);
            PointName destinationPointName = inflectRelation.get(pointName);
            Point destinationPoint = map.get(destinationPointName);

            point.addInflectPoint(destinationPoint);
        }
    }

    public static Point getStandByPoint() {
        return standByPoint;
    }

    public static int getTotalPointQuantities() {
        return points.keySet().size();
    }

    public static Point get(PointName pointName) {
        return points.get(pointName);
    }
}
