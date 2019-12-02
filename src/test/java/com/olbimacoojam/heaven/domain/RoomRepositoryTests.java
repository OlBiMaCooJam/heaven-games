package com.olbimacoojam.heaven.domain;

import org.junit.jupiter.api.Test;

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

}