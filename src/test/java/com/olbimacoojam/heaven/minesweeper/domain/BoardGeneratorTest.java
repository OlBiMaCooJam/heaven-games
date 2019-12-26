package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class BoardGeneratorTest {
    Map<Position, Block> blocks = new HashMap<>();

    {
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
        Block block = Block.of(BlockStatus.UNCLICKED, 0, false);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Position position = Position.of(i, j);
                if (!blocks.containsKey(position)) {
                    blocks.put(position, block);
                }
            }
        }
    }

    Board expected = Board.of(blocks);

    @Test
    void generate() {
        BoardSpecification boardSpecification = BoardSpecification.of(10, 10, 2);
        MinePositionGenerator minePositionGenerator = (rows, columns, numMines) -> {
            List<Position> positions = Arrays.asList(Position.of(1, 1), Position.of(2, 1));
            Set<Position> minePositions = new HashSet<>(positions);
            return new MinePositions(minePositions);
        };

        BoardGenerator boardGenerator = new BoardGenerator(boardSpecification, minePositionGenerator);
        Board board = boardGenerator.generate();

        assertThat(board).isEqualTo(expected);
    }
}