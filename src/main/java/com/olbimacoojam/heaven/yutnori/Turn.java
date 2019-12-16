package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;

public class Turn {
    private final YutnoriParticipant yutnoriParticipant;

    public Turn(YutnoriParticipant yutnoriParticipant) {
        this.yutnoriParticipant = yutnoriParticipant;
    }

    public boolean isRightTurn(User thrower) {
        return yutnoriParticipant.isRightThrower(thrower);
    }
}
