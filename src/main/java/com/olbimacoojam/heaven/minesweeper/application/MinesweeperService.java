package com.olbimacoojam.heaven.minesweeper.application;

import com.olbimacoojam.heaven.minesweeper.domain.*;
import com.olbimacoojam.heaven.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinesweeperService {
    private final RoomService roomService;

    public Minesweeper startGame(Integer roomId, final MinesweeperCreateRequest request) {
        BoardSpecification boardSpecification = BoardSpecification.of(request.getRows(), request.getColumns(), request.getMines());
        MinePositionGenerator minePositionGenerator = new RandomMinePositionGenerator();
        BoardGenerator boardGenerator = new BoardGenerator(boardSpecification, minePositionGenerator);

        Minesweeper minesweeper = (Minesweeper) roomService.findById(roomId).getGame();
        minesweeper.registerBoard(boardGenerator.generate());
        return minesweeper;
    }

    public ClickResponse click(Integer roomId, Click click) {
        Minesweeper minesweeper = (Minesweeper) roomService.findById(roomId).getGame();

        ClickedBlocks clickedBlocks = minesweeper.click(click);
        return new ClickResponse(clickedBlocks, minesweeper.getMinesweeperStatus());
    }
}
