package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.minesweeper.domain.exception.PositionOutOfBoardException;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode
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

    public boolean canClick(Position position) {
        return board.containsKey(position) && !isClicked(position);
    }

    private boolean isClicked(Position position) {
        return board.get(position).isClicked();
    }

    public MinesweeperStatus getCurrentStatus() {
        MinesweeperStatus minesweeperStatus = MinesweeperStatus.WIN;

        for (Block block : board.values()) {
            if (block.isMine()) {
                return MinesweeperStatus.LOSE;
            }

            if (!block.isClicked() && !block.isUnclickedMine()) {
                minesweeperStatus = MinesweeperStatus.PLAYING;
            }
        }

        return minesweeperStatus;
    }
}