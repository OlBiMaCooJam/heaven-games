package com.olbimacoojam.heaven.controller;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomRepository;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.game.User;
import com.olbimacoojam.heaven.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketRoomController {
//    private final RoomRepository roomRepository;
//    private final ModelMapper modelMapper;
    private final RoomService roomService;

    public WebsocketRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @MessageMapping("/rooms/{roomId}")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto enterRoom(@DestinationVariable int roomId) {
        return roomService.subscribe(roomId);

//        Room room = roomRepository.findById(roomId);
//        room.join(new User());
//        System.out.println("here!!");
//        return modelMapper.map(room, RoomResponseDto.class);
    }

    @MessageMapping("/rooms/{roomId}/leave")
    @SendTo("/topic/rooms/{roomId}")
    public RoomResponseDto leaveRoom(@DestinationVariable int roomId) {
        return roomService.unsubscribe(roomId);
    }
}
