package com.olbimacoojam.heaven.minesweeper.domain;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfUsersException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.UserNotMatchException;

import java.util.List;

public class Minesweeper implements Game {
    private static final int MINESWEEPER_PLAYER_NUMBER = 1;
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

    public Board getBoard() {
        return this.board;
    }
}
