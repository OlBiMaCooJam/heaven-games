package com.olbimacoojam.heaven.minesweeper.application;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.minesweeper.domain.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class MinesweeperService {

    public Board createGame(final List<User> players, final User user, final MinesweeperCreateRequest request) {
        BoardSpecification boardSpecification = BoardSpecification.of(request.getRows(), request.getColumns(), request.getMines());
        MinePositionGenerator minePositionGenerator = new RandomMinePositionGenerator();
        BoardGenerator boardGenerator = new BoardGenerator(boardSpecification, minePositionGenerator);

        Minesweeper minesweeper = Minesweeper.newGame(user, boardGenerator.generate());
        minesweeper.initialize(players);
        return minesweeper.getBoard();
    }
}
