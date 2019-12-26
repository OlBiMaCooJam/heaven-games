package com.olbimacoojam.heaven.yutnori.yut;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomYutThrowStrategy implements YutThrowStrategy {

    @Override
    public Yut throwYut() {
        List<Yut> yuts = Arrays.stream(Yut.values())
                .flatMap(yut -> IntStream.range(0, yut.getWeight()).mapToObj(i -> yut))
                .collect(Collectors.toList());
        Collections.shuffle(yuts);
        return yuts.get(0);
    }
}
