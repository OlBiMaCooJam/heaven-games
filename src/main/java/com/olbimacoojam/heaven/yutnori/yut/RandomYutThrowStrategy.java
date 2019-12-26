package com.olbimacoojam.heaven.yutnori.yut;

import java.util.Collections;
import java.util.List;

public class RandomYutThrowStrategy implements YutThrowStrategy {
    private List<Yut> probableYuts;

    public RandomYutThrowStrategy() {
        this.probableYuts = Yut.getProbableYuts();
    }

    @Override
    public Yut throwYut() {
        Collections.shuffle(probableYuts);
        return probableYuts.get(0);
    }
}
