package com.olbimacoojam.heaven.minesweeper.application;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MinesweeperCreateRequest {
    private Integer columns;
    private Integer rows;
    private Integer mines;
}
