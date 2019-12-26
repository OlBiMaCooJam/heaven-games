package com.olbimacoojam.heaven.draw.domain;

import lombok.Getter;

@Getter
public enum Lot {
    PASS("통과"), WHACK("꽝");

    private String lot;

    Lot(String lot) {
        this.lot = lot;
    }
}
