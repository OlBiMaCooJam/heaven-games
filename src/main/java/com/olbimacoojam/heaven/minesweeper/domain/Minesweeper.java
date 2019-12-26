package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.minesweeper.domain.exception.GameOverException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfUsersException;
import lombok.Getter;

import java.util.List;

public class Minesweeper implements Game {
    private static final int MINESWEEPER_PLAYER_NUMBER = 1;

    @Getter
    private MinesweeperStatus minesweeperStatus;
    private Board board;
    private User user;

    public Minesweeper() {
        this.minesweeperStatus = MinesweeperStatus.PLAYING;
    }

    @Override
    public void initialize(final List<User> players) {
        checkNumberOfPlayers(players);
        this.user = players.get(0);
    }

    public void registerBoard(Board board) {
        this.board = board;
    }

    private void checkNumberOfPlayers(List<User> players) {
        if (players.size() != MINESWEEPER_PLAYER_NUMBER) {
            throw new InvalidNumberOfUsersException(MINESWEEPER_PLAYER_NUMBER, players.size());
        }
    }

    public ClickedBlocks click(Click click) {
        checkGameOver();

        ClickedBlocks clickedBlocks = clickInternal(click);

        minesweeperStatus = board.getCurrentStatus();
        return clickedBlocks;
    }

    private ClickedBlocks clickInternal(Click click) {
        Block clickedBlock = board.click(click);

        ClickedBlocks clickedBlocks = ClickedBlocks.of(click.getPosition(), clickedBlock);
        if (clickedBlock.isBlankBlock() && click.getClickType().isLeftClick()) {
            return clickedBlocks.putAll(propagateBlanks(click));
        }

        return clickedBlocks;
    }

    private ClickedBlocks propagateBlanks(Click click) {
        Position position = click.getPosition();

        return position.getAroundPositions().stream()
                .filter(board::canClick)
                .map(pos -> clickInternal(Click.leftClickOn(pos)))
                .reduce(ClickedBlocks.newClickedBlocks(), ClickedBlocks::putAll);
    }

    private void checkGameOver() {
        if (minesweeperStatus.isGameOver()) {
            throw new GameOverException();
        }
    }
}
