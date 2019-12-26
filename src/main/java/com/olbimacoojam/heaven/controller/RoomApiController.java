package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.game.GameKind2;
import com.olbimacoojam.heaven.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/rooms")
@RestController
public class RoomApiController {

    private final RoomService roomService;

    @Autowired
    public RoomApiController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity save(@RequestParam("gameKind") GameKind2 gameKind) {
        RoomResponseDto roomResponseDto = roomService.createRoom(gameKind);
        return ResponseEntity.created(URI.create("/rooms/" + roomResponseDto.getId())).build();
    }

    @GetMapping
    public List<RoomResponseDto> listByGame(@RequestParam("gameKind") GameKind2 gameKind) {
        return roomService.findByGameKind(gameKind);
    }
}
