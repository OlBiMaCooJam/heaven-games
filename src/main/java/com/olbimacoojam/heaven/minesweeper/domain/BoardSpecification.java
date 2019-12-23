package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidBoardSizeException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfMinesException;
import lombok.Getter;

@Getter
public class BoardSpecification {
    private static final Integer MAX_NUMBER_OF_ROWS = 30;
    private static final Integer MIN_NUMBER_OF_ROWS = 10;
    private static final Integer MAX_NUMBER_OF_COLUMNS = 30;
    private static final Integer MIN_NUMBER_OF_COLUMNS = 10;

    private Integer rows;
    private Integer columns;
    private Integer mines;

    private BoardSpecification(Integer rows, Integer columns, Integer mines) {
        checkSpecification(rows, columns, mines);

        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
    }

    public static BoardSpecification of(Integer rows, Integer columns, Integer mines) {
        return new BoardSpecification(rows, columns, mines);
    }

    private void checkSpecification(Integer rows, Integer columns, Integer mines) {
        checkRows(rows);
        checkColumns(columns);
        checkMines(rows, columns, mines);
    }

    private void checkRows(Integer rows) {
        if (rows > MAX_NUMBER_OF_ROWS || rows < MIN_NUMBER_OF_ROWS) {
            throw new InvalidBoardSizeException();
        }
    }

    private void checkColumns(Integer columns) {
        if (columns > MAX_NUMBER_OF_COLUMNS || columns < MIN_NUMBER_OF_COLUMNS) {
            throw new InvalidBoardSizeException();
        }
    }

    private void checkMines(Integer rows, Integer columns, Integer mines) {
        if (rows * columns <= mines) {
            throw new InvalidNumberOfMinesException();
        }
    }
}
