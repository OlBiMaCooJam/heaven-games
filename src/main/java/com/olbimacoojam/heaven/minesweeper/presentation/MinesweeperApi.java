package com.olbimacoojam.heaven.minesweeper.presentation;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.minesweeper.application.MinesweeperCreateRequest;
import com.olbimacoojam.heaven.minesweeper.application.MinesweeperService;
import com.olbimacoojam.heaven.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RestController
@RequestMapping("/rooms/{roomId}/minesweeper")
@RequiredArgsConstructor
public class MinesweeperApi {
    private final MinesweeperService minesweeperService;
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity newGame(HttpSession httpSession, @PathVariable Integer roomId, @RequestBody MinesweeperCreateRequest minesweeperCreateRequest) {
        User user = (User) httpSession.getAttribute(UserSession.USER_SESSION);
//        Room room = roomService.findById(roomId);
        minesweeperService.createGame(Collections.singletonList(user), user, minesweeperCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
