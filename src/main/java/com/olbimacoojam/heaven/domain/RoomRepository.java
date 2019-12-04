package com.olbimacoojam.heaven.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoomRepository {
    private final Map<Integer, Room> rooms;

    public RoomRepository() {
        this.rooms = new ConcurrentHashMap<>();
    }

    public Room save(Room room) {
        if (isRoomExist(room)) {
            throw new RoomSaveFailException();
        }

        return rooms.put(room.getId(), room);
    }

    private boolean isRoomExist(Room room) {
        return rooms.containsKey(room.getId());
    }

    public List<Room> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(rooms.values()));
    }

    public Room findById(int roomId) {
        return rooms.get(roomId);
    }
}
