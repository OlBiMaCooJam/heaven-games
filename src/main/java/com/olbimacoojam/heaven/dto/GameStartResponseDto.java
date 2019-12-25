package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.game.GameKind;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class GameStartResponseDto {

    private Boolean isGameStart;
    private int numberOfPlayers;
    private GameKind gameKind;

    public GameStartResponseDto(boolean isGameStart, int numberOfPlayers, GameKind gameKind) {
        this.isGameStart = isGameStart;
        this.numberOfPlayers = numberOfPlayers;
        this.gameKind = gameKind;
    }

    public boolean isGameStart() {
        return isGameStart;
    }
}
