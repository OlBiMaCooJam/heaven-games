package com.olbimacoojam.heaven.minesweeper.application;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.minesweeper.domain.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MinesweeperService {
    private final MinesweeperRepository minesweeperRepository;

    public Board createGame(final List<User> players, final User user, final MinesweeperCreateRequest request) {
        MinePositionGenerator minePositionGenerator = new RandomMinePositionGenerator(request.getRows(), request.getColumns(), request.getMines());
        BoardGenerator boardGenerator = new BoardGenerator(request.getRows(), request.getColumns(), minePositionGenerator);

        Minesweeper minesweeper = Minesweeper.newGame(user, boardGenerator.generate());
        minesweeper.initialize(players);
        return minesweeper.getBoard();
    }
}
