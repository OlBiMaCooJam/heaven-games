package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.Getter;

@Getter
public class Block {
    private final BlockStatus blockStatus;
    private final Integer numberOfAroundMines;
    private final boolean isMine;

    private Block(BlockStatus blockStatus, Integer numberOfAroundMines, boolean isMine) {
        this.numberOfAroundMines = numberOfAroundMines;
        this.blockStatus = blockStatus;
        this.isMine = isMine;
    }

    public static Block of(BlockStatus blockStatus, Integer numberOfAroundMines, boolean isMine) {
        return new Block(blockStatus, numberOfAroundMines, isMine);
    }
}
