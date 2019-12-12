package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.Occupation.Occupation;
import lombok.Getter;

@Getter
public class MafiaParticipant {
    private User player;
    private Occupation occupation;

    public MafiaParticipant(User player, Occupation occupation) {
        this.player = player;
        this.occupation = occupation;
    }
}
