package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClickedBlocksTest {
    @Test
    void putAll() {
        Position position = Position.of(1, 2);
        Block block = Block.of(BlockStatus.CLICKED, 1, false);

        ClickedBlocks expected = ClickedBlocks.of(position, block);
        ClickedBlocks clickedBlocks = ClickedBlocks.of(position, block);

        assertThat(ClickedBlocks.newClickedBlocks().putAll(clickedBlocks)).isEqualTo(expected);
    }
}