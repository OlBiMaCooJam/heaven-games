package com.olbimacoojam.heaven.yutnori.yut;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class Yuts {

    private final List<Yut> yuts;

    public Yuts() {
        this(new ArrayList<>());
    }

    private Yuts(List<Yut> yuts) {
        this.yuts = yuts;
    }

    public boolean isThrowAvailable() {
        return yuts.isEmpty() || isLastYutOrMo();
    }

    private boolean isLastYutOrMo() {
        Yut lastYut = yuts.get(yuts.size() - 1);
        return lastYut.equals(Yut.YUT) || lastYut.equals(Yut.MO);
    }

    public void add(Yut yut) {
        yuts.add(yut);
    }

    public boolean contains(Yut yut) {
        return yuts.contains(yut);
    }

    public boolean remove(Yut yut) {
        return yuts.remove(yut);
    }

    public boolean isRemaining() {
        return !yuts.isEmpty();
    }
}
