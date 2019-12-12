package com.olbimacoojam.heaven.minesweeper.domain;

import java.util.Set;

public interface MinePositionGenerator {
    Set<Position> generate();
}
