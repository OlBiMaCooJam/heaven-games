package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomFactory;
import com.olbimacoojam.heaven.domain.RoomRepository;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.dto.*;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.RandomYutThrowStrategy;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.YutThrowStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomFactory roomFactory;
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    private final UserService userService;
    private final YutThrowStrategy yutThrowStrategy;

    @Autowired
    public RoomService(RoomFactory roomFactory, ModelMapper modelMapper, UserService userService, RoomRepository roomRepository) {
        this.roomFactory = roomFactory;
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.yutThrowStrategy = new RandomYutThrowStrategy();
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

    public RoomResponseDto unsubscribe(int roomId) {
        Room room = roomRepository.findById(roomId);
        room.leave();
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

    public GameStartResponseDtos initiateGame(int roomId) {
        Room room = roomRepository.findById(roomId);
        GameStartResponseDtos gameStartResponseDtos = room.initiateGame();
        return gameStartResponseDtos;
    }

    public YutResponse throwYut(int roomId, Long userId) {
        Room room = roomRepository.findById(roomId);
        User thrower = userService.findById(userId);
        return room.throwYut(thrower);
    }

    public MoveResultDtos movePiece(int roomId, Long userId, MoveRequestDto moveRequestDto) {
        Room room = roomRepository.findById(roomId);
        User mover = userService.findById(userId);
        PointName pointName = PointName.get(moveRequestDto.getPointName());
        Yut yut = Yut.get(moveRequestDto.getYut());
        return room.movePiece(mover, pointName, yut);
    }
}
