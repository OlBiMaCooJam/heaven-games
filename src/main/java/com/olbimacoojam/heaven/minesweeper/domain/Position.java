package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Position {
    private Integer x;
    private Integer y;

    private Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(Integer x, Integer y) {
        return new Position(x, y);
    }

    public List<Position> getAroundPositions() {
        List<Position> positions = IntStream.rangeClosed(x - 1, x + 1)
                .boxed()
                .flatMap(numberX -> IntStream.rangeClosed(y - 1, y + 1)
                        .filter(numberY -> !numberX.equals(x) || numberY != y)
                        .mapToObj(numberY -> Position.of(numberX, numberY)))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(positions);
    }
}
