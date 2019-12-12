package com.olbimacoojam.heaven.minesweeper.domain;

import java.util.Map;

public class Board {
    private final Map<Position, Block> board;

    public Board(final Map<Position, Block> board) {
        this.board = board;
    }

    public static Board of(final Map<Position, Block> board) {
        return new Board(board);
    }
}