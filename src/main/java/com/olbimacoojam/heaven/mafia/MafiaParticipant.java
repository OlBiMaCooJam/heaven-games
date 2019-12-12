package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.Occupation.Occupation;
import lombok.Getter;

@Getter
public class MafiaParticipant {
    private final User player;
    private final Occupation occupation;

    public MafiaParticipant(final User player, final Occupation occupation) {
        this.player = player;
        this.occupation = occupation;
    }
}
