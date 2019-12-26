package com.olbimacoojam.heaven.draw.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Lots {
    private List<Lot> lots;

    public Lots() {
        this.lots = Collections.emptyList();
    }

    private Lots(List<Lot> lots) {
        this.lots = lots;
    }

    public static Lots of(int personCount, int whackCount) {
        List<Lot> lots = new ArrayList<>();
        for (int i = 0; i < personCount - whackCount; i++) {
            lots.add(new Pass());
        }
        for (int i = 0; i < whackCount; i++) {
            lots.add(new Whack());
        }
        Collections.shuffle(lots);
        return new Lots(lots);
    }

    public void initialize() {
        List<Lot> lots = new ArrayList<>();
        lots.add(new Pass());
        lots.add(new Pass());
        lots.add(new Pass());
        lots.add(new Whack());

        Collections.shuffle(lots);
        this.lots = lots;
    }
}
