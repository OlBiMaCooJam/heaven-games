package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.minesweeper.domain.exception.PositionOutOfBoardException;
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

    public Block click(Click click) {
        Position position = click.getPosition();
        ClickType clickType = click.getClickType();
        if (!board.containsKey(position)) {
            throw new PositionOutOfBoardException();
        }

        Block block = board.get(position).click(clickType);
        board.put(position, block);
        return block;
    }

    public boolean isClicked(Position position) {
        return board.get(position).isClicked();
    }

    public boolean contains(Position position) {
        return board.containsKey(position);
    }
}