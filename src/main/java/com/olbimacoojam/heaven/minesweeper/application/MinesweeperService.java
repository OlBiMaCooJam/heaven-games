package com.olbimacoojam.heaven.minesweeper.application;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.minesweeper.domain.*;
import com.olbimacoojam.heaven.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MinesweeperService {
    private final RoomService roomService;

    public Minesweeper createGame(final List<User> players, final User user, final MinesweeperCreateRequest request) {
        BoardSpecification boardSpecification = BoardSpecification.of(request.getRows(), request.getColumns(), request.getMines());
        MinePositionGenerator minePositionGenerator = new RandomMinePositionGenerator();
        BoardGenerator boardGenerator = new BoardGenerator(boardSpecification, minePositionGenerator);

        Minesweeper minesweeper = Minesweeper.newGame(user, boardGenerator.generate());
        minesweeper.initialize(players);
        return minesweeper;
    }

    public Block click(User user, Integer roomId, Click click, Minesweeper minesweeper) {
//        Room room = roomService.findById(roomId);
//        Minesweeper minesweeper = (Minesweeper) room.getGame();

        return minesweeper.click(user, click);
    }
}
