package com.olbimacoojam.heaven.yutnori.yutnorigame;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.exception.IncorrectTurnException;
import com.olbimacoojam.heaven.yutnori.exception.NotExistYutException;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.Yuts;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Turn {
    private final YutnoriParticipant yutnoriParticipant;
    private final Yuts thrownYuts;
    private boolean canThrow;

    public Turn(YutnoriParticipant yutnoriParticipant) {
        this.yutnoriParticipant = yutnoriParticipant;
        this.thrownYuts = new Yuts();
        this.canThrow = true;
    }

    public Yut saveOneThrow(User thrower, Yut yut) {
        if (canThrow(thrower)) {
            thrownYuts.add(yut);
            canThrow = thrownYuts.isThrowAvailable();
            return yut;
        }
        throw new IncorrectTurnException("당신의 턴이 아니거나 지금은 말을 놓을 때입니다.");
    }

    private boolean canThrow(User thrower) {
        return isRightTurn(thrower) && canThrow;
    }

    private boolean isRightTurn(User user) {
        return yutnoriParticipant.isRightThrower(user);
    }

    public boolean consumeYut(User user, Yut yut) {
        if (canMove(user, yut)) {
            return thrownYuts.remove(yut);
        }
        throw new NotExistYutException(yut.name());
    }

    private boolean canMove(User user, Yut yut) {
        return isRightTurn(user) && !canThrow && thrownYuts.contains(yut);
    }

    public Color getTeamColor() {
        return yutnoriParticipant.getColor();
    }

    public Turn next(MoveResults moveResults) {
        return null;
    }
}
