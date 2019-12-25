package com.olbimacoojam.heaven.draw.domain;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfUsersException;
import lombok.Getter;

import java.util.List;

@Getter
public class Draw implements Game {
    private Lots lots;

    public Draw() {
        this.lots = new Lots();
    }

    @Override
    public void initialize(List<User> players) {
        checkNumberOfPlayers(players);
        lots.initialize();
    }

    private void checkNumberOfPlayers(List<User> players) {
        if (players.size() != 1) {
            throw new InvalidNumberOfUsersException(1, players.size());
        }
    }

    public void startGame(int personCount, int whackCount) {
        this.lots = Lots.of(personCount, whackCount);
    }
}
