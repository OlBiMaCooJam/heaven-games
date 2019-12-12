package com.olbimacoojam.heaven.minesweeper.domain;

public interface MinePositionGenerator {
    MinePositions generate(final Integer rows, final Integer columns, final Integer numMines);
}
