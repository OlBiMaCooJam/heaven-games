package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomFactory;
import com.olbimacoojam.heaven.domain.RoomRepository;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.game.Game;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomFactory roomFactory;
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    private final UserService userService;

    public RoomService(RoomFactory roomFactory, ModelMapper modelMapper, RoomRepository roomRepository, UserService userService) {
        this.roomFactory = roomFactory;
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
        this.userService = userService;
    }

    public RoomResponseDto createRoom() {
        Room room = roomFactory.makeNextRoom();
        roomRepository.save(room);
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public List<RoomResponseDto> findAll() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(room -> modelMapper.map(room, RoomResponseDto.class))
                .collect(Collectors.toList());
    }

    public RoomResponseDto subscribe(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId);
        room.join(userService.findById(userId));
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public RoomResponseDto unsubscribe(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId);
        room.leave(userId);
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId);
    }

    public int startGame(Long roomId) {
        Room room = roomRepository.findById(roomId);
        room.startGame();
        List<User> players = room.getPlayers();
        Game game = room.getGame();
        game.initialize(players);

        return players.size();
    }
}
