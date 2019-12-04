package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomRepository;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketRoomController {
    private final RoomRepository roomRepository;

    public WebsocketRoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @MessageMapping("/rooms/{roomId}")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto enterRoom(@DestinationVariable int roomId) {
        Room room = roomRepository.findById(roomId);
        return new RoomResponseDto(room.getId());
    }
}
