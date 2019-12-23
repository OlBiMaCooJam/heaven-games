package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BlockTest {
    private Block mineBlock = Block.of(BlockStatus.MINE, 0, true);
    private Block blankBlock = Block.of(BlockStatus.CLICKED, 0, false);
    private Block clickedBlockWithAround1 = Block.of(BlockStatus.CLICKED, 1, false);

    @ParameterizedTest
    @MethodSource("provideBlocksForLeftClick")
    void clickLeft(Block input, Block expected) {
        assertThat(input.click(ClickType.LEFT)).isEqualTo(expected);
    }

    @Test
    void clickRight() {
        ClickType rightCLick = ClickType.RIGHT;

        Block unclicked = Block.of(BlockStatus.UNCLICKED, 0, false);
        Block flag = unclicked.click(rightCLick);
        Block questionMark = flag.click(rightCLick);

        assertThat(flag).isEqualTo(Block.of(BlockStatus.FLAG, 0, false));
        assertThat(questionMark).isEqualTo(Block.of(BlockStatus.QUESTION_MARK, 0, false));
    }

    @Test
    void isMine() {
        assertThat(mineBlock.isMine()).isTrue();
        assertThat(blankBlock.isMine()).isFalse();
        assertThat(clickedBlockWithAround1.isMine()).isFalse();
    }

    @Test
    void isBlankBlock() {
        assertThat(blankBlock.isBlankBlock()).isTrue();
        assertThat(mineBlock.isBlankBlock()).isFalse();
        assertThat(clickedBlockWithAround1.isBlankBlock()).isFalse();
    }

    @Test
    void isClicked() {
        assertThat(clickedBlockWithAround1.isClicked()).isTrue();
        assertThat(blankBlock.isClicked()).isTrue();
        assertThat(mineBlock.isClicked()).isFalse();
    }

    private static Stream<Arguments> provideBlocksForLeftClick() {
        Block mine = Block.of(BlockStatus.MINE, 0, true);
        Block clicked = Block.of(BlockStatus.CLICKED, 0, false);
        return Stream.of(
                Arguments.of(Block.of(BlockStatus.UNCLICKED, 0, false), clicked),
                Arguments.of(Block.of(BlockStatus.UNCLICKED, 0, true), mine),
                Arguments.of(Block.of(BlockStatus.FLAG, 0, false), clicked),
                Arguments.of(Block.of(BlockStatus.FLAG, 0, true), mine),
                Arguments.of(Block.of(BlockStatus.QUESTION_MARK, 0, false), clicked),
                Arguments.of(Block.of(BlockStatus.QUESTION_MARK, 0, true), mine),
                Arguments.of(Block.of(BlockStatus.CLICKED, 0, false), clicked),
                Arguments.of(Block.of(BlockStatus.MINE, 0, true), mine)
        );
    }
}