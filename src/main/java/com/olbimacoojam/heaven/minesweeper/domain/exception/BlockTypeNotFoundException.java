package com.olbimacoojam.heaven.minesweeper.domain.exception;

public class BlockTypeNotFoundException extends RuntimeException {
    public BlockTypeNotFoundException() {
        super("해당하는 블록 타입을 찾을 수 없습니다.");
    }
}
