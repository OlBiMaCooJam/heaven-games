package com.olbimacoojam.heaven.minesweeper.presentation;

import com.olbimacoojam.heaven.minesweeper.application.ClickResponse;
import com.olbimacoojam.heaven.minesweeper.application.MinesweeperCreateRequest;
import com.olbimacoojam.heaven.minesweeper.application.MinesweeperService;
import com.olbimacoojam.heaven.minesweeper.domain.Click;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms/{roomId}/minesweeper")
@RequiredArgsConstructor
public class MinesweeperApi {
    private final MinesweeperService minesweeperService;

    @PostMapping
    public ResponseEntity newGame(@PathVariable Integer roomId, @RequestBody MinesweeperCreateRequest minesweeperCreateRequest) {
        minesweeperService.startGame(roomId, minesweeperCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping
    public ResponseEntity click(@PathVariable Integer roomId, @RequestBody Click click) {
        ClickResponse clickResponse = minesweeperService.click(roomId, click);
        return ResponseEntity.ok(clickResponse);
    }
}
