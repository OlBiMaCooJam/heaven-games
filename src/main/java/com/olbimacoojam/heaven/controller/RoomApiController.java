package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.olbimacoojam.heaven.service.RoomService;

import java.net.URI;
import java.util.List;

@RequestMapping("/rooms")
@RestController
public class RoomApiController {
//    private final RoomRepository roomRepository;
//    private final RoomFactory roomFactory;
    private final RoomService roomService;
//    private final ModelMapper modelMapper;

    @Autowired
    public RoomApiController(RoomService roomService) {
        this.roomService = roomService;
//        this.roomRepository = roomRepository;
//        this.roomFactory = roomFactory;
//        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity save() {
        RoomResponseDto roomResponseDto = roomService.createRoom();
        return ResponseEntity.created(URI.create("rooms/" + roomResponseDto.getId())).build();
    }

    @GetMapping
    public List<RoomResponseDto> list() {
        return roomService.findAll();
    }
}
