package com.olbimacoojam.heaven.yutnori.yut;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomYutThrowStrategy implements YutThrowStrategy {
    @Override
    public Yut throwYut() {
        List<Yut> yuts = Arrays.asList(Yut.values());
        Collections.shuffle(yuts);
        return yuts.get(2);
    }
}
