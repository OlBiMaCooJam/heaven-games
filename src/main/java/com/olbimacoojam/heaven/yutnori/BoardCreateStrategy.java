package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.board.Board;
import com.olbimacoojam.heaven.yutnori.participant.YutnoriParticipants;

public interface BoardCreateStrategy {

    Board createBoard(YutnoriParticipants yutnoriParticipants);
}
