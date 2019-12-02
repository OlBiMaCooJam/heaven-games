package com.olbimacoojam.heaven.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Room {
    private final int id;

    public Room(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
