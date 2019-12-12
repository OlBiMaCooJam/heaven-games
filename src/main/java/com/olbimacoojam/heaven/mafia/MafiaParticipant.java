package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;
import lombok.Getter;

@Getter
public class MafiaParticipant {
    private final User player;

    private final OccupationType occupationType;

    public MafiaParticipant(final User player, final OccupationType occupationType) {
        this.player = player;
        this.occupationType = occupationType;
    }
}
