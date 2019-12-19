package com.olbimacoojam.heaven.minesweeper.application;

import com.olbimacoojam.heaven.minesweeper.domain.ClickedBlocks;
import lombok.Getter;

@Getter
public class ClickResponse {
    private final ClickedBlocks clickedBlocks;
    private final boolean isGameOver;

    public ClickResponse(ClickedBlocks clickedBlocks, boolean isGameOver) {
        this.clickedBlocks = clickedBlocks;
        this.isGameOver = isGameOver;
    }
}
