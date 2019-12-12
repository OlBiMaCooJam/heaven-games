package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.game.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomRepositoryTests {

    @Test
    void save_test() {
        RoomRepository roomRepository = new RoomRepository();
        assertDoesNotThrow(() -> roomRepository.save(new Room(1, createGame())));
    }

    @Test
    void fail_save_test() {
        RoomRepository roomRepository = new RoomRepository();
        roomRepository.save(new Room(1, createGame()));
        assertThrows(RoomSaveFailException.class, () -> roomRepository.save(new Room(1, createGame())));
    }

    @Test
    void list_test() {
        RoomRepository roomRepository = new RoomRepository();
        roomRepository.save(new Room(1, createGame()));
        roomRepository.save(new Room(2, createGame()));
        roomRepository.save(new Room(3, createGame()));

        assertThat(roomRepository.findAll().size()).isEqualTo(3);
    }

    private Game createGame() {
        return new Game() {
            @Override
            public void initialize(List<User> players) {
            }
        };
    }
}