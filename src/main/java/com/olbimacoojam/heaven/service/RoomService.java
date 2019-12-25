package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomFactory;
import com.olbimacoojam.heaven.domain.RoomRepository;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.dto.GameStartResponseDto;
import com.olbimacoojam.heaven.dto.RoomResponseDto;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.game.GameKind;
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
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public Room findById(int roomId) {
        return roomRepository.findById(roomId);
    }

    public int startGame(int roomId) {
        Room room = roomRepository.findById(roomId);
        room.startGame();
        List<User> players = room.getPlayers();
        Game game = room.getGame();
        game.initialize(players);

        return players.size();
    }

    public GameStartResponseDto startGame2(int roomId) {
        Room room = roomRepository.findById(roomId);
        boolean isGameStart = room.startGame();

        return new GameStartResponseDto(isGameStart, room.countPlayers(), room.getGameKind());
    }
}
