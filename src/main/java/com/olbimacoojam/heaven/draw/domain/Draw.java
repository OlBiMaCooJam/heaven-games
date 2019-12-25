package com.olbimacoojam.heaven.draw.domain;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfUsersException;
import com.olbimacoojam.heaven.minesweeper.domain.exception.UserNotMatchException;
import lombok.Getter;

import java.util.List;

@Getter
public class Draw implements Game {
    private User user;
    private Lots lots;

    public Draw() {
        this.lots = new Lots();
    }

    public Draw(User user, Lots lots) {
        this.user = user;
        this.lots = lots;
    }

    @Override
    public void initialize(List<User> players) {
//        checkUser(players);
//        lots.initialize();
    }

    private void checkUser(List<User> players) {
        checkNumberOfPlayers(players);
        checkSameUser(players);
    }

    private void checkNumberOfPlayers(List<User> players) {
        if (players.size() != 1) {
            throw new InvalidNumberOfUsersException(1, players.size());
        }
    }

    private void checkSameUser(List<User> players) {
        if (!players.contains(user)) {
            throw new UserNotMatchException();
        }
    }
}
