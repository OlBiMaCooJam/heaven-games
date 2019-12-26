package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoomResponseDto {

    private int id;
    private List<UserResponseDto> players;

    public RoomResponseDto(int id, List<UserResponseDto> players) {
        this.id = id;
        this.players = players;
    }
}
