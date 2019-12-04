package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomFactory;
import com.olbimacoojam.heaven.domain.RoomRepository;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/rooms")
@RestController
public class RoomApiController {
    private final RoomRepository roomRepository;
    private final RoomFactory roomFactory;
    private final ModelMapper modelMapper;

    public RoomApiController(RoomRepository roomRepository, RoomFactory roomFactory, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.roomFactory = roomFactory;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity save() {
        Room room = roomFactory.makeNextRoom();
        roomRepository.save(room);
        return ResponseEntity.created(URI.create("rooms/" + room.getId())).build();
    }

    @GetMapping
    public List<RoomResponseDto> list() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(room -> modelMapper.map(room, RoomResponseDto.class))
                .collect(Collectors.toList());
    }
}
