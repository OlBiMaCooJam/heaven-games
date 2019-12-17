package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Block {
    @Getter(AccessLevel.NONE)
    private final boolean isMine;

    private final BlockStatus blockStatus;
    private final Integer numberOfAroundMines;

    private Block(BlockStatus blockStatus, Integer numberOfAroundMines, boolean isMine) {
        this.numberOfAroundMines = numberOfAroundMines;
        this.blockStatus = blockStatus;
        this.isMine = isMine;
    }

    public static Block of(BlockStatus blockStatus, Integer numberOfAroundMines, boolean isMine) {
        return new Block(blockStatus, numberOfAroundMines, isMine);
    }

    public Block click(ClickType clickedType) {
        BlockStatus nextBlockStatus = this.blockStatus.nextStatus(clickedType, isMine);
        return new Block(nextBlockStatus, numberOfAroundMines, isMine);
    }

    public boolean isClickedMine() {
        return isMine && blockStatus.isClicked();
    }
}
