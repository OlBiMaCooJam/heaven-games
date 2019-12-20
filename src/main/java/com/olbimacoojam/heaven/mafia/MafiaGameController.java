package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.service.RoomService;
import com.olbimacoojam.heaven.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MafiaGameController {
    private final RoomService roomService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/rooms/{roomId}/mafia")
    public ResponseEntity startGame(@PathVariable int roomId) {
        int numberOfPlayers = roomService.startGame(roomId);

        return ResponseEntity.ok()
                .body(numberOfPlayers);
    }
}
