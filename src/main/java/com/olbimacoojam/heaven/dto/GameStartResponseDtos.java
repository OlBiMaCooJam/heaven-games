package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GameStartResponseDtos {
    private String startingMessage;
    private List<GameStartResponseDto> gameStartResponseDtos;

    public GameStartResponseDtos(List<GameStartResponseDto> gameStartResponseDtos) {
        this.startingMessage = "게임이 시작되었습니다!";
        this.gameStartResponseDtos = gameStartResponseDtos;
    }

    public int size() {
        return gameStartResponseDtos.size();
    }

    public boolean containsUser(String userName) {
        return gameStartResponseDtos.stream()
                .anyMatch(gameStartResponseDto -> gameStartResponseDto.isName(userName));
    }

    public Long getFirstId() {
        return gameStartResponseDtos.get(0).getId();
    }

    public Long getSecondId() {
        return gameStartResponseDtos.get(1).getId();
    }
}
