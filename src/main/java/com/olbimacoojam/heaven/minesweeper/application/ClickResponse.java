package com.olbimacoojam.heaven.minesweeper.application;

import com.olbimacoojam.heaven.minesweeper.domain.ClickedBlocks;
import com.olbimacoojam.heaven.minesweeper.domain.MinesweeperStatus;
import lombok.Getter;

@Getter
public class ClickResponse {
    private final ClickedBlocks clickedBlocks;
    private final MinesweeperStatus minesweeperStatus;

    public ClickResponse(ClickedBlocks clickedBlocks, MinesweeperStatus minesweeperStatus) {
        this.clickedBlocks = clickedBlocks;
        this.minesweeperStatus = minesweeperStatus;
    }
}
