package com.olbimacoojam.heaven.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomRepositoryTests {

    @Test
    void save_test() {
        RoomRepository roomRepository = new RoomRepository();
        assertDoesNotThrow(() -> roomRepository.save(new Room(1)));
    }

    @Test
    void fail_save_test() {
        RoomRepository roomRepository = new RoomRepository();
        roomRepository.save(new Room(1));
        assertThrows(RoomSaveFailException.class, () -> roomRepository.save(new Room(1)));
    }

    @Test
    void list_test() {
        RoomRepository roomRepository = new RoomRepository();
        roomRepository.save(new Room(1));
        roomRepository.save(new Room(2));
        roomRepository.save(new Room(3));

        assertThat(roomRepository.findAll().size()).isEqualTo(3);
    }
}