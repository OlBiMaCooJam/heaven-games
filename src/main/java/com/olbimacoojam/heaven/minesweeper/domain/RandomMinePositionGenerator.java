package com.olbimacoojam.heaven.minesweeper.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomMinePositionGenerator implements MinePositionGenerator {
    private final Random random = new Random();
    private final int rows;
    private final int columns;
    private final int numMines;

    public RandomMinePositionGenerator(int rows, int columns, int numMines) {
        this.rows = rows;
        this.columns = columns;
        this.numMines = numMines;
    }

    @Override
    public Set<Position> generate() {
        Set<Position> minePositions = new HashSet<>();

        while (minePositions.size() < this.numMines) {
            minePositions.add(generatePosition());
        }
        return Collections.unmodifiableSet(minePositions);
    }

    private Position generatePosition() {
        int x = random.nextInt(columns);
        int y = random.nextInt(rows);

        return Position.of(x, y);
    }
}
