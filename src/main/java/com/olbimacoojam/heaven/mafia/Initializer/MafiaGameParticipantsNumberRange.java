package com.olbimacoojam.heaven.mafia.Initializer;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.MafiaParticipant;

import java.util.List;

public enum MafiaGameParticipantsNumberRange {
    FOUR_TO_FIVE(new MafiaMinimumInitializer()),
    SIX_TO_EIGHT(new MafiaMiddleInitializer()),
    NINE_TO_TEN(new MafiaMaximumInitializer());

    private MafiaInitializer initializer;

    MafiaGameParticipantsNumberRange(MafiaInitializer initializer) {
        this.initializer = initializer;
    }

    public static List<MafiaParticipant> initialize(List<User> players) {
        return findInitializeByNumber(players.size()).initialize(players);
    }

    private static MafiaInitializer findInitializeByNumber(int number) {
        if (number <= 5) {
            return FOUR_TO_FIVE.initializer;
        }
        if (number <= 8) {
            return SIX_TO_EIGHT.initializer;
        }
        return NINE_TO_TEN.initializer;
    }
}
