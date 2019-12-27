package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomFactory;
import com.olbimacoojam.heaven.domain.RoomRepository;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.dto.GameStartResponseDto;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.game.GameKind2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomFactory roomFactory;
    private final RoomRepository roomRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public RoomService(RoomFactory roomFactory, RoomRepository roomRepository, UserService userService, ModelMapper modelMapper) {
        this.roomFactory = roomFactory;
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public RoomResponseDto createRoom(GameKind2 gameKind) {
        Room room = roomFactory.makeNextRoom(gameKind);
        roomRepository.save(room);
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public List<RoomResponseDto> findByGameKind(GameKind2 gameKind) {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .filter(room -> room.isGameKind2(gameKind))
                .map(room -> modelMapper.map(room, RoomResponseDto.class))
                .collect(Collectors.toList());
    }

    public RoomResponseDto subscribe(int roomId, Long userId) {
        Room room = roomRepository.findById(roomId);
        User user = userService.findById(userId);
        room.join(user);
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public RoomResponseDto unsubscribe(int roomId, Long userId) {
        Room room = roomRepository.findById(roomId);
        User user = userService.findById(userId);
        room.leave(user);
        if(room.getPlayers().size() == 0 && !room.getGame().isStart()) {
            roomRepository.remove(room);
        }
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public Room findById(int roomId) {
        return roomRepository.findById(roomId);
    }

    public GameStartResponseDto startGame(int roomId) {
        Room room = roomRepository.findById(roomId);
        boolean isGameStart = room.startGame();

        return new GameStartResponseDto(isGameStart, room.countPlayers(), room.gameKind2());
    }
}
