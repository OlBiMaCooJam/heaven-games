package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.game.User;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Room {
    private final int id;
    private final List<User> players;
    private final Game game;

    public Room(int id, Game game) {
        this.players = new CopyOnWriteArrayList<>();
        this.id = id;
        this.game = game;
    }
}
