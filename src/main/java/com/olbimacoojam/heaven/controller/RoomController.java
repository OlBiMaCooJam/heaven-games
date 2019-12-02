package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomFactory;
import com.olbimacoojam.heaven.domain.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RoomController {
    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final RoomFactory roomFactory;

    public RoomController(RoomRepository roomRepository, RoomFactory roomFactory) {
        this.roomRepository = roomRepository;
        this.roomFactory = roomFactory;
    }

    @PostMapping("/room")
    public ResponseEntity save() {
        Room room = roomFactory.makeNextRoom();
        roomRepository.save(room);
        return ResponseEntity.created(URI.create("room/" + room.getId())).build();
    }

}
