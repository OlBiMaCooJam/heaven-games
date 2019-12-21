package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.mafia.MafiaGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoomRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(RoomRepository.class);
    private final Map<Long, Room> rooms;

    public RoomRepository() {
        rooms = new ConcurrentHashMap<>();
        rooms.put(1L, new Room(1L, new MafiaGame()));
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

    public Room findById(Long roomId) {
        return rooms.get(roomId);
    }
}
