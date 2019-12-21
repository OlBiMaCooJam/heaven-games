package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.mafia.MafiaGame;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RoomFactory {
    private final AtomicLong id = new AtomicLong();

    /*구현체를 아직 만들지 않아 익명 클래스로 대체*/
    public Room makeNextRoom() {
        return new Room(id.getAndIncrement(), new MafiaGame());
    }
}
