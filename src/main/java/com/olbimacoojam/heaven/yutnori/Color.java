package com.olbimacoojam.heaven.yutnori;

public enum Color {

    BLACK, RED;

    public static Color get(int i) {
        i = i % values().length;
        return values()[i];
    }
}
