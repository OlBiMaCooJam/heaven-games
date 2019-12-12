package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaGameParticipantsNumberRange;

import java.util.List;

public class MafiaGame implements Game {
    private List<MafiaParticipant> players;

    /**
     * 마피아 게임에 참여하고 있는 Player 들의 역할을 정하는 메서드
     */
    @Override
    public void initialize(List<User> players) {
        List<MafiaParticipant> mafiaParticipants = MafiaGameParticipantsNumberRange.initialize(players);
    }

    public boolean isEnd() {
        return true;
    }

    public MafiaResult getResult() {
        return MafiaResult.CITIZEN_WIN;
    }
}
