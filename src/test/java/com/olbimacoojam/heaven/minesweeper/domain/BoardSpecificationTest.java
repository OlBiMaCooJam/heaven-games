package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidBoardSizeException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfMinesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardSpecificationTest {
    @Test
    void validBoardSizeAndNumberOfMine() {
        assertDoesNotThrow(() -> BoardSpecification.of(30, 30, 899));
        assertDoesNotThrow(() -> BoardSpecification.of(10, 10, 99));
    }

    @Test
    void invalidBoardSizeOverMaxRow() {
        assertThrows(InvalidBoardSizeException.class, () -> BoardSpecification.of(31, 30, 10));
    }

    @Test
    void invalidBoardSizeOverMaxColumn() {
        assertThrows(InvalidBoardSizeException.class, () -> BoardSpecification.of(30, 31, 10));
    }

    @Test
    void invalidBoardSizeUnderMinRow() {
        assertThrows(InvalidBoardSizeException.class, () -> BoardSpecification.of(9, 30, 10));
    }

    @Test
    void invalidBoardSizeUnderMinColumn() {
        assertThrows(InvalidBoardSizeException.class, () -> BoardSpecification.of(30, 9, 10));
    }

    @Test
    void invalidMineSize() {
        assertThrows(InvalidNumberOfMinesException.class, () -> BoardSpecification.of(10, 10, 100));
    }
}