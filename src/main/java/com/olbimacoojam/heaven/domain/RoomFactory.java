package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.draw.domain.Draw;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class RoomFactory {
    private final AtomicLong id = new AtomicLong();

    /*구현체를 아직 만들지 않아 익명 클래스로 대체*/
    public Room makeNextRoom() {
        return new Room(id.getAndIncrement(), new Draw());
    }
}
