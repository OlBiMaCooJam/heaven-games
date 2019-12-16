package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.yut.Yut;

public class Turn {
    private final YutnoriParticipant yutnoriParticipant;
    private final Yut yut;

    public Turn(YutnoriParticipant yutnoriParticipant) {
        this(yutnoriParticipant, Yut.NOT_THROWN);
    }

    public Turn(YutnoriParticipant yutnoriParticipant, Yut yut) {
        this.yutnoriParticipant = yutnoriParticipant;
        this.yut = yut;
    }

    public boolean isRightTurn(User thrower) {
        return (yutnoriParticipant.isRightThrower(thrower)) && (yut == Yut.NOT_THROWN);
    }

    public Turn processOneThrow(Yut yut) {
        return new Turn(yutnoriParticipant, yut);
    }
}
