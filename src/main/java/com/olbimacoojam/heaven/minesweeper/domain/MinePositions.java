package com.olbimacoojam.heaven.minesweeper.domain;

import java.util.Set;

public class MinePositions {
    private final Set<Position> minePositions;

    public MinePositions(final Set<Position> minePositions) {
        this.minePositions = minePositions;
    }

    public boolean isMine(Position position) {
        return minePositions.contains(position);
    }
}
