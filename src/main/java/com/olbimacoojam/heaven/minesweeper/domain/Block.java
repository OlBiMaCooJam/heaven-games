package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.Getter;

@Getter
public class Block {
    private final BlockType blockType;
    private final BlockStatus blockStatus;

    private Block(BlockType blockType, BlockStatus blockStatus) {
        this.blockType = blockType;
        this.blockStatus = blockStatus;
    }

    public static Block of(BlockType blockType, BlockStatus blockStatus) {
        return new Block(blockType, blockStatus);
    }
}
