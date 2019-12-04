package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoomResponseDto {
    private int id;
    private List<UserResponseDto> players;

    public RoomResponseDto(int id, List<UserResponseDto> players) {
        this.id = id;
        this.players = players;
    }

    public List<UserResponseDto> getPlayers() {
        return players;
    }
}
