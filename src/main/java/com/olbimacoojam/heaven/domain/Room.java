package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.game.Game;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

//todo : input 유효성 체크하기
@Getter
public class Room {
    private static final Logger LOGGER = LoggerFactory.getLogger(Room.class);

    private final Long id;
    private final List<User> players;
    private final Game game;

    private final Set<WebSocketSession> webSocketSessions = new HashSet<>();

    public Room(Long id, Game game) {
        this.players = new CopyOnWriteArrayList<>();
        this.id = id;
        this.game = game;
    }

    public void join(User player) {
        players.add(player);
    }

    public void leave(Long userId) {
        User leavedUser = players.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        players.remove(leavedUser);
    }

    public void startGame() {
        game.initialize(players);
    }
}
