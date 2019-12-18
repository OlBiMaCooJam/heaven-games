package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.minesweeper.domain.exception.GameOverException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfUsersException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.UserNotMatchException;

import java.util.Collections;
import java.util.List;

public class Minesweeper implements Game {
    private static final int MINESWEEPER_PLAYER_NUMBER = 1;
    private boolean isGameOver = false;
    private final Board board;
    private final User user;

    private Minesweeper(final User user, final Board board) {
        this.board = board;
        this.user = user;
    }

    public static Minesweeper newGame(final User user, final Board board) {
        return new Minesweeper(user, board);
    }

    @Override
    public void initialize(final List<User> players) {
        checkUser(players);
    }

    private void checkUser(List<User> players) {
        checkNumberOfPlayers(players);
        checkSameUser(players);
    }

    private void checkNumberOfPlayers(List<User> players) {
        if (players.size() != MINESWEEPER_PLAYER_NUMBER) {
            throw new InvalidNumberOfUsersException(MINESWEEPER_PLAYER_NUMBER, players.size());
        }
    }

    private void checkSameUser(List<User> players) {
        if (!players.contains(user)) {
            throw new UserNotMatchException();
        }
    }

    public ClickedBlocks click(User user, Click click) {
        checkUser(Collections.singletonList(user));
        checkGameOver();
        Block clickedBlock = board.click(click);

        if (clickedBlock.isClickedMine()) {
            isGameOver = true;
        }

        ClickedBlocks clickedBlocks = ClickedBlocks.of(click.getPosition(), clickedBlock);
        if (clickedBlock.isBlankBlock() && click.getClickType().isLeftClick()) {
            return clickedBlocks.putAll(propagateBlanks(click));
        }

        return clickedBlocks;
    }

    private ClickedBlocks propagateBlanks(Click click) {
        Position position = click.getPosition();

        return position.getAroundPositions().stream()
                .filter(pos -> board.contains(pos) && !board.isClicked(pos))
                .map(pos -> click(user, Click.leftClickOn(pos)))
                .reduce(ClickedBlocks.newClickedBlocks(), ClickedBlocks::putAll);
    }

    private void checkGameOver() {
        if (isGameOver) {
            throw new GameOverException();
        }
    }
}
