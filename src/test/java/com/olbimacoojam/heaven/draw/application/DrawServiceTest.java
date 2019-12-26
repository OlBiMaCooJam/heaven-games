package com.olbimacoojam.heaven.draw.application;

import com.olbimacoojam.heaven.domain.Room;
import com.olbimacoojam.heaven.domain.RoomFactory;
import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.draw.domain.Draw;
import com.olbimacoojam.heaven.draw.domain.Lot;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.service.RoomService;
import com.olbimacoojam.heaven.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class DrawServiceTest {

    @InjectMocks
    private DrawService drawService;

    @Mock
    private RoomService roomService;

    @Mock
    private UserService userService;

    @Mock
    private RoomFactory roomFactory;

    @Mock
    private Room mockRoom;

    @Test
    void updateGame() {
        Game game = new Draw();
        Room room = new Room(1L, game);
        User user = new User(1L, "martin", "token");
        Draw draw = (Draw)room.getGame();

        given(roomService.findById(any(Long.class))).willReturn(room);
        given(mockRoom.getGame()).willReturn(game);
        given(userService.findById(any(Long.class))).willReturn(user);

        DrawResponse drawResponse = drawService.updateGame(1L, 1L, new DrawCreateRequest(10, 1));

        assertThat(drawResponse.getLots().getLots().stream().filter(Lot.PASS::equals).count()).isEqualTo(9L);
        assertThat(drawResponse.getLots().getLots().stream().filter(Lot.WHACK::equals).count()).isEqualTo(1L);
    }

    @Test
    void initGame() {
        Game game = new Draw();
        Room room = new Room(1L, game);
        User user = new User(1L, "martin", "token");
        Draw draw = (Draw)room.getGame();

        given(roomService.findById(any(Long.class))).willReturn(room);
        given(mockRoom.getGame()).willReturn(game);
        given(userService.findById(any(Long.class))).willReturn(user);

        DrawResponse drawResponse = drawService.initGame(1L, 1L);

        assertThat(drawResponse.getLots().getLots().stream().filter(Lot.PASS::equals).count()).isEqualTo(3L);
        assertThat(drawResponse.getLots().getLots().stream().filter(Lot.WHACK::equals).count()).isEqualTo(1L);
    }
}