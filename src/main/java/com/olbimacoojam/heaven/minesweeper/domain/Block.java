package com.olbimacoojam.heaven.minesweeper.domain;

public class Block {
    private final BlockType blockType;
    private final BlockStatus blockStatus = BlockStatus.UNCLICKED;

    private Block(BlockType blockType) {
        this.blockType = blockType;
    }

    public static Block of(BlockType blockType) {
        return new Block(blockType);
    }
}
