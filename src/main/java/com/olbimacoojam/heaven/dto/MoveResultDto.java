package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.Color;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.Route;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class MoveResultDto {
    private final String color;
    private final List<String> route;

    public MoveResultDto(Color color, Route route) {
        this.color = color.name();
        this.route = route.getRoute()
                .stream()
                .map(pointName -> pointName.name())
                .collect(Collectors.toList());
    }

    public String getColor() {
        return color;
    }

    public List<String> getRoute() {
        return route;
    }
}
