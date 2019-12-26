package com.olbimacoojam.heaven.yutnori.yut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomYutThrowStrategy implements YutThrowStrategy {
    private List<Integer> probabilities;

    public RandomYutThrowStrategy() {
        this.probabilities = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17));
    }

    @Override
    public Yut throwYut() {
        Collections.shuffle(probabilities);
        int probability = probabilities.get(0);
        if (probability >= 1 && probability <= 4) {
            return Yut.DO;
        }
        if (probability >= 5 && probability <= 10) {
            return Yut.GAE;
        }
        if (probability >= 11 && probability <= 14) {
            return Yut.GUL;
        }
        if (probability == 15) {
            return Yut.YUT;
        }
        if (probability == 16) {
            return Yut.MO;
        }
        return Yut.BACKDO;
    }
}
