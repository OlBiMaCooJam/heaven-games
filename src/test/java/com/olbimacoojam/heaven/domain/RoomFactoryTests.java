package com.olbimacoojam.heaven.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomFactoryTests {
    @Test
    @DisplayName("Room생성시 아이디가 고유한지 확인")
    void create_unique_rooms() {
        RoomFactory roomFactory = new RoomFactory();
        Room room1 = roomFactory.makeNextRoom();
        Room room2 = roomFactory.makeNextRoom();
        assertThat(room1.getId()).isNotEqualTo(room2.getId());
    }

}