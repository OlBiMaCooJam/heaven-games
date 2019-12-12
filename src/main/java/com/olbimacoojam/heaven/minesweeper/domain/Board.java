package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.Getter;

import java.util.Map;

@Getter
public class Board {
    private final Map<Position, Block> board;

    private Board(final Map<Position, Block> board) {
        this.board = board;
    }

    public static Board of(final Map<Position, Block> board) {
        return new Board(board);
    }
}