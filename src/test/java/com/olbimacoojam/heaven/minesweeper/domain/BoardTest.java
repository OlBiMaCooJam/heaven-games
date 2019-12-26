package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.minesweeper.domain.exception.PositionOutOfBoardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {
    Board board;

    @BeforeEach
    void init() {
        Map<Position, Block> blocks = new HashMap<>();
        blocks.put(Position.of(0, 0), Block.of(BlockStatus.UNCLICKED, 1, false));
        blocks.put(Position.of(1, 0), Block.of(BlockStatus.UNCLICKED, 2, false));
        blocks.put(Position.of(2, 0), Block.of(BlockStatus.UNCLICKED, 2, false));
        blocks.put(Position.of(3, 0), Block.of(BlockStatus.UNCLICKED, 1, false));
        blocks.put(Position.of(0, 1), Block.of(BlockStatus.UNCLICKED, 1, false));
        blocks.put(Position.of(1, 1), Block.of(BlockStatus.UNCLICKED, 1, true));
        blocks.put(Position.of(2, 1), Block.of(BlockStatus.UNCLICKED, 1, true));
        blocks.put(Position.of(3, 1), Block.of(BlockStatus.UNCLICKED, 1, false));
        blocks.put(Position.of(0, 2), Block.of(BlockStatus.UNCLICKED, 1, false));
        blocks.put(Position.of(1, 2), Block.of(BlockStatus.UNCLICKED, 2, false));
        blocks.put(Position.of(2, 2), Block.of(BlockStatus.UNCLICKED, 2, false));
        blocks.put(Position.of(3, 2), Block.of(BlockStatus.UNCLICKED, 1, false));
        blocks.put(Position.of(0, 3), Block.of(BlockStatus.UNCLICKED, 0, false));
        blocks.put(Position.of(1, 3), Block.of(BlockStatus.UNCLICKED, 0, false));
        blocks.put(Position.of(2, 3), Block.of(BlockStatus.UNCLICKED, 0, false));
        blocks.put(Position.of(3, 3), Block.of(BlockStatus.UNCLICKED, 0, false));

        board = Board.of(blocks);
    }

    @Test
    void click() {
        Block clickedBlock = Block.of(BlockStatus.CLICKED, 1, false);
        assertThat(board.click(Click.leftClickOn(Position.of(0, 0)))).isEqualTo(clickedBlock);
        assertThrows(PositionOutOfBoardException.class, () -> board.click(Click.leftClickOn(Position.of(-1, 0))));
    }

    @Test
    void canClick() {
        Position clickablePosition = Position.of(0, 0);
        Position unclickablePosition = Position.of(-1, 0);

        assertThat(board.canClick(clickablePosition)).isTrue();

        board.click(Click.leftClickOn(clickablePosition));

        assertThat(board.canClick(clickablePosition)).isFalse();
        assertThat(board.canClick(unclickablePosition)).isFalse();
    }

    @Test
    void getCurrentStatus_PLAYING() {
        assertThat(board.getCurrentStatus()).isEqualTo(MinesweeperStatus.PLAYING);
        board.click(Click.leftClickOn(Position.of(0, 0)));
        board.click(Click.leftClickOn(Position.of(1, 0)));
        board.click(Click.leftClickOn(Position.of(2, 0)));
        board.click(Click.leftClickOn(Position.of(3, 0)));
        assertThat(board.getCurrentStatus()).isEqualTo(MinesweeperStatus.PLAYING);
    }

    @Test
    void getCurrentStatus_WIN() {
        board.click(Click.leftClickOn(Position.of(0, 0)));
        board.click(Click.leftClickOn(Position.of(1, 0)));
        board.click(Click.leftClickOn(Position.of(2, 0)));
        board.click(Click.leftClickOn(Position.of(3, 0)));
        board.click(Click.leftClickOn(Position.of(0, 1)));
        board.click(Click.leftClickOn(Position.of(3, 1)));
        board.click(Click.leftClickOn(Position.of(0, 2)));
        board.click(Click.leftClickOn(Position.of(1, 2)));
        board.click(Click.leftClickOn(Position.of(2, 2)));
        board.click(Click.leftClickOn(Position.of(3, 2)));
        board.click(Click.leftClickOn(Position.of(0, 3)));
        board.click(Click.leftClickOn(Position.of(1, 3)));
        board.click(Click.leftClickOn(Position.of(2, 3)));
        board.click(Click.leftClickOn(Position.of(3, 3)));
        assertThat(board.getCurrentStatus()).isEqualTo(MinesweeperStatus.WIN);
    }

    @Test
    void getCurrentStatus_LOSE() {
        board.click(Click.leftClickOn(Position.of(1, 1)));
        assertThat(board.getCurrentStatus()).isEqualTo(MinesweeperStatus.LOSE);
    }
}