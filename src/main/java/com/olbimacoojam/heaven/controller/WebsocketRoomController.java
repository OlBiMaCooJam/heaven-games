package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.service.RoomService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketRoomController {
    private final RoomService roomService;

    public WebsocketRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @MessageMapping("/rooms/{roomId}")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto enterRoom(@DestinationVariable int roomId) {
        return roomService.subscribe(roomId);
    }

    @MessageMapping("/rooms/{roomId}/leave")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto leaveRoom(@DestinationVariable int roomId) {
        return roomService.unsubscribe(roomId);
    }
}
