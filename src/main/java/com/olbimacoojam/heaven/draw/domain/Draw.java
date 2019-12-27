package com.olbimacoojam.heaven.draw.domain;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.draw.domain.exception.InvalidWhackCountException;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.minesweeper.domain.exception.InvalidNumberOfUsersException;
import lombok.Getter;

import java.util.List;

@Getter
public class Draw implements Game {
    private static final int DRAW_PLAYER_NUMBER = 1;

    private Lots lots;
    private boolean isStart;

    public Draw() {
        this.lots = new Lots();
        isStart = false;
    }

    @Override
    public void initialize(List<User> players) {
        checkNumberOfPlayers(players);
        lots.initialize();
        isStart = true;
    }

    @Override
    public boolean isStart() {
        return isStart;
    }

    private void checkNumberOfPlayers(List<User> players) {
        if (players.size() != DRAW_PLAYER_NUMBER) {
            throw new InvalidNumberOfUsersException(1, players.size());
        }
    }

    public void startGame(int personCount, int whackCount) {
        if (personCount < whackCount) {
            throw new InvalidWhackCountException(personCount, whackCount);
        }
        this.lots = Lots.of(personCount, whackCount);
    }
}
