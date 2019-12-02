package com.olbimacoojam.heaven.domain;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoomFactory {
    private final AtomicInteger id = new AtomicInteger();

    public Room makeNextRoom() {
        return new Room(id.getAndIncrement());
    }
}
