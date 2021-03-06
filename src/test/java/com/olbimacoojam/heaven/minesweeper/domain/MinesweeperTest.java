package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.minesweeper.domain.exception.GameOverException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfUsersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MinesweeperTest {
    User user1 = new User(1L, "user1", "token1");
    User user2 = new User(2L, "user2", "token2");
    Map<Position, Block> unclickedBlocks;
    Map<Position, Block> clickedBlocks;

    @BeforeEach
    void init() {
        unclickedBlocks = new HashMap<>();
        unclickedBlocks.put(Position.of(0, 0), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(1, 0), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(2, 0), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(3, 0), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(4, 0), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(0, 1), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(1, 1), Block.of(BlockStatus.UNCLICKED, 1, false));
        unclickedBlocks.put(Position.of(2, 1), Block.of(BlockStatus.UNCLICKED, 1, false));
        unclickedBlocks.put(Position.of(3, 1), Block.of(BlockStatus.UNCLICKED, 1, false));
        unclickedBlocks.put(Position.of(4, 1), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(0, 2), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(1, 2), Block.of(BlockStatus.UNCLICKED, 1, false));
        unclickedBlocks.put(Position.of(2, 2), Block.of(BlockStatus.UNCLICKED, 0, true));
        unclickedBlocks.put(Position.of(3, 2), Block.of(BlockStatus.UNCLICKED, 1, false));
        unclickedBlocks.put(Position.of(4, 2), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(0, 3), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(1, 3), Block.of(BlockStatus.UNCLICKED, 1, false));
        unclickedBlocks.put(Position.of(2, 3), Block.of(BlockStatus.UNCLICKED, 1, false));
        unclickedBlocks.put(Position.of(3, 3), Block.of(BlockStatus.UNCLICKED, 1, false));
        unclickedBlocks.put(Position.of(4, 3), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(0, 4), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(1, 4), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(2, 4), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(3, 4), Block.of(BlockStatus.UNCLICKED, 0, false));
        unclickedBlocks.put(Position.of(4, 4), Block.of(BlockStatus.UNCLICKED, 0, false));

        clickedBlocks = new HashMap<>();
        clickedBlocks.put(Position.of(0, 0), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(1, 0), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(2, 0), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(3, 0), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(4, 0), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(0, 1), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(1, 1), Block.of(BlockStatus.CLICKED, 1, false));
        clickedBlocks.put(Position.of(2, 1), Block.of(BlockStatus.CLICKED, 1, false));
        clickedBlocks.put(Position.of(3, 1), Block.of(BlockStatus.CLICKED, 1, false));
        clickedBlocks.put(Position.of(4, 1), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(0, 2), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(1, 2), Block.of(BlockStatus.CLICKED, 1, false));
        clickedBlocks.put(Position.of(3, 2), Block.of(BlockStatus.CLICKED, 1, false));
        clickedBlocks.put(Position.of(4, 2), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(0, 3), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(1, 3), Block.of(BlockStatus.CLICKED, 1, false));
        clickedBlocks.put(Position.of(2, 3), Block.of(BlockStatus.CLICKED, 1, false));
        clickedBlocks.put(Position.of(3, 3), Block.of(BlockStatus.CLICKED, 1, false));
        clickedBlocks.put(Position.of(4, 3), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(0, 4), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(1, 4), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(2, 4), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(3, 4), Block.of(BlockStatus.CLICKED, 0, false));
        clickedBlocks.put(Position.of(4, 4), Block.of(BlockStatus.CLICKED, 0, false));

    }

    @Test
    void initializeSameUser() {
        Board board = Board.of(new HashMap<>());

        Minesweeper minesweeper = new Minesweeper();
        minesweeper.registerBoard(board);
        assertDoesNotThrow(() -> minesweeper.initialize(Collections.singletonList(user1)));
    }

    @Test
    void initializeInvalidUserSize() {
        Board board = Board.of(new HashMap<>());

        Minesweeper minesweeper = new Minesweeper();
        minesweeper.initialize(Collections.singletonList(user1));
        minesweeper.registerBoard(board);
        assertThrows(InvalidNumberOfUsersException.class, () -> minesweeper.initialize(Arrays.asList(user1, user2)));
    }

    @Test
    void clickPropagation() {
        Board board = Board.of(unclickedBlocks);

        Minesweeper minesweeper = new Minesweeper();
        minesweeper.registerBoard(board);

        ClickedBlocks expected = clickedBlocks.entrySet().stream()
                .map(entry -> ClickedBlocks.of(entry.getKey(), entry.getValue()))
                .reduce(ClickedBlocks.newClickedBlocks(), ClickedBlocks::putAll);

        assertThat(minesweeper.click(Click.leftClickOn(Position.of(0, 0)))).isEqualTo(expected);
    }

    @Test
    void gameOver() {
        Board board = Board.of(unclickedBlocks);

        Minesweeper minesweeper = new Minesweeper();
        minesweeper.registerBoard(board);
        assertThat(minesweeper.getMinesweeperStatus().isGameOver()).isFalse();

        minesweeper.click(Click.leftClickOn(Position.of(2, 2)));
        assertThat(minesweeper.getMinesweeperStatus().isGameOver()).isTrue();

        assertThrows(GameOverException.class, () -> minesweeper.click(Click.leftClickOn(Position.of(2, 2))));
    }
}