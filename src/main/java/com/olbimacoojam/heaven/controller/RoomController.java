package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomFactory;
import com.olbimacoojam.heaven.domain.RoomRepository;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/rooms")
    public ResponseEntity save() {
        Room room = roomFactory.makeNextRoom();
        roomRepository.save(room);
        return ResponseEntity.created(URI.create("rooms/" + room.getId())).build();
    }

    @GetMapping("/rooms")
    public List<RoomResponseDto> list() {
        List<Room> rooms = roomRepository.getAll();
        return rooms.stream()
                .mapToInt(Room::getId)
                .mapToObj(RoomResponseDto::new)
                .collect(Collectors.toList());
    }
}
