package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.domain.RoomState;
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
    private RoomState roomState;
    private List<UserResponseDto> players;

    public RoomResponseDto(int id, RoomState roomState, List<UserResponseDto> players) {
        this.id = id;
        this.roomState = roomState;
        this.players = players;
    }
}
