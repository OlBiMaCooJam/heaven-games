package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ClickedBlocks {
    private final Map<Position, Block> clickedBlocks;

    private ClickedBlocks(Map<Position, Block> clickedBlocks) {
        this.clickedBlocks = clickedBlocks;
    }

    private ClickedBlocks() {
        this.clickedBlocks = new HashMap<>();
    }

    public static ClickedBlocks newClickedBlocks() {
        return new ClickedBlocks();
    }

    public static ClickedBlocks of(Position position, Block block) {
        Map<Position, Block> clickedBlocks = new HashMap<>();
        clickedBlocks.put(position, block);
        return new ClickedBlocks(clickedBlocks);
    }

    public ClickedBlocks putAll(ClickedBlocks clickedBlocks) {
        ClickedBlocks blocks = new ClickedBlocks();
        blocks.clickedBlocks.putAll(this.clickedBlocks);
        blocks.clickedBlocks.putAll(clickedBlocks.clickedBlocks);

        return blocks;
    }
}
