package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;
import lombok.Getter;

@Getter
public class MafiaParticipant {
    private final User player;
    private boolean alive;
    private boolean shield;

    private final OccupationType occupationType;

    public MafiaParticipant(final User player, final OccupationType occupationType) {
        this.player = player;
        this.occupationType = occupationType;
        alive = true;
        shield = occupationType.equals(OccupationType.SOLDIER);
    }

    public boolean dead() {
        if (shield) {
            shield = false;
            return false;
        }
        alive = false;
        return true;
    }

    public void applyShield() {
        shield = true;
    }

    public void removeShield() {
        shield = false;
    }

    public boolean hasShield() {
        return shield;
    }

    public boolean isAlive() {
        return alive;
    }


}
