package com.olbimacoojam.heaven.minesweeper.domain;

import java.util.HashMap;
import java.util.Map;

public class BoardGenerator {
    private final BoardSpecification boardSpecification;
    private final MinePositionGenerator minePositionGenerator;

    public BoardGenerator(final BoardSpecification boardSpecification, final MinePositionGenerator minePositionGenerator) {
        this.boardSpecification = boardSpecification;
        this.minePositionGenerator = minePositionGenerator;
    }

    public Board generate() {
        Map<Position, Block> board = new HashMap<>();
        MinePositions minePositions = minePositionGenerator.generate(boardSpecification.getRows(), boardSpecification.getColumns(), boardSpecification.getMines());

        for (int y = 0; y < boardSpecification.getRows(); y++) {
            board.putAll(generateRow(y, boardSpecification.getColumns(), minePositions));
        }

        return Board.of(board);
    }

    private Map<Position, Block> generateRow(Integer y, Integer columns, MinePositions minePositions) {
        Map<Position, Block> row = new HashMap<>();

        for (int x = 0; x < columns; x++) {
            Position position = Position.of(x, y);
            Integer numberOfMines = getNumberOfAroundMinesOf(position, minePositions).intValue();
            Block block = Block.of(BlockStatus.UNCLICKED, numberOfMines, minePositions.isMine(position));
            row.put(position, block);
        }

        return row;
    }

    private Long getNumberOfAroundMinesOf(Position position, MinePositions minePositions) {
        return position.getAroundPositions().stream()
                .filter(minePositions::isMine)
                .count();
    }
}
