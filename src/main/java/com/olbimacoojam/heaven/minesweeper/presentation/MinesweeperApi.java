package com.olbimacoojam.heaven.minesweeper.presentation;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserSession;
import com.olbimacoojam.heaven.minesweeper.application.MinesweeperCreateRequest;
import com.olbimacoojam.heaven.minesweeper.application.MinesweeperService;
import com.olbimacoojam.heaven.minesweeper.domain.Board;
import com.olbimacoojam.heaven.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/minesweeper")
@RequiredArgsConstructor
public class MinesweeperApi {
    private final MinesweeperService minesweeperService;
    private final RoomService roomService;

    @GetMapping("/rooms/{roomId}/new")
    public Board newGame(HttpSession httpSession, @PathVariable Integer roomId, MinesweeperCreateRequest request) {
        User user = (User) httpSession.getAttribute(UserSession.USER_SESSION);
        Room room = roomService.findById(roomId);
        return minesweeperService.createGame(room.getPlayers(), user, request);
    }
}
