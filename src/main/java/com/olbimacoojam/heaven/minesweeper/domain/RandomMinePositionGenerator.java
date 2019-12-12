package com.olbimacoojam.heaven.minesweeper.domain;

import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@NoArgsConstructor
public class RandomMinePositionGenerator implements MinePositionGenerator {
    private final Random random = new Random();

    @Override
    public MinePositions generate(final Integer rows, final Integer columns, final Integer numMines) {
        Set<Position> minePositions = new HashSet<>();

        while (minePositions.size() < numMines) {
            minePositions.add(generatePosition(rows, columns));
        }
        return new MinePositions(Collections.unmodifiableSet(minePositions));
    }

    private Position generatePosition(final Integer rows, final Integer columns) {
        Integer x = random.nextInt(columns);
        Integer y = random.nextInt(rows);

        return Position.of(x, y);
    }
}
