package com.olbimacoojam.heaven.minesweeper.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BoardGenerator {
    private final int rows;
    private final int columns;
    private final MinePositionGenerator minePositionGenerator;

    public BoardGenerator(final int rows, final int columns, MinePositionGenerator minePositionGenerator) {
        this.rows = rows;
        this.columns = columns;
        this.minePositionGenerator = minePositionGenerator;
    }

    public Board generate() {
        Map<Position, Block> board = new HashMap<>();
        Set<Position> minePositions = minePositionGenerator.generate();

        for (int y = 0; y < rows; y++) {
            board.putAll(generateRow(y, minePositions));
        }

        return Board.of(board);
    }

    private Map<Position, Block> generateRow(int y, Set<Position> minePositions) {
        Map<Position, Block> row = new HashMap<>();

        for (int x = 0; x < columns; x++) {
            Position position = Position.of(x, y);
            Block block = generateBlock(position, minePositions);
            row.put(position, block);
        }

        return row;
    }

    private Block generateBlock(Position position, Set<Position> minePositions) {
        if (minePositions.contains(position)) {
            return Block.of(BlockType.MINE, BlockStatus.UNCLICKED);
        }

        return Block.of(BlockType.BLOCK, BlockStatus.UNCLICKED);
    }
}
