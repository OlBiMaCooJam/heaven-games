package com.olbimacoojam.heaven.minesweeper.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MinesweeperCreateRequest {
    private final Integer columns;
    private final Integer rows;
    private final Integer mines;
}
