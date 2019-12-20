package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaInitializerRegistry;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMaximumInitializer;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMiddleInitializer;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMinimumInitializer;

import java.util.List;

public class MafiaGame implements Game {
    private static final MafiaInitializerRegistry MAFIA_INITIALIZER_REGISTRY = new MafiaInitializerRegistry();
    private List<MafiaParticipant> mafiaParticipants;

    public MafiaGame() {
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMinimumInitializer());
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMiddleInitializer());
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMaximumInitializer());
    }

    /**
     * 마피아 게임에 참여하고 있는 Player 들의 역할을 정하는 메서드
     */
    @Override
    public void initialize(List<User> players) {
        mafiaParticipants = MAFIA_INITIALIZER_REGISTRY.getMafiaInitializer(players.size()).initialize(players);
    }

    public boolean isEnd() {
        return true;
    }

    public MafiaResult getResult() {
        return MafiaResult.CITIZEN_WIN;
    }

    public List<MafiaParticipant> getMafiaParticipants() {
        return mafiaParticipants;
    }
}
