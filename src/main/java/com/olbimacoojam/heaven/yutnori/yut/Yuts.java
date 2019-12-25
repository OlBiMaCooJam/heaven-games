package com.olbimacoojam.heaven.yutnori.yut;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Yuts add(Yut yut) {
        List<Yut> yuts = this.yuts.stream()
                .collect(Collectors.toList());
        yuts.add(yut);
        return new Yuts(yuts);
    }

    public boolean notHave(Yut yut) {
        return !yuts.contains(yut);
    }

    public Yuts remove(Yut yut) {
        List<Yut> yuts = this.yuts.stream()
                .collect(Collectors.toList());
        yuts.remove(yut);
        return new Yuts(yuts);
    }

    public boolean isRemaining() {
        return !yuts.isEmpty();
    }
}
