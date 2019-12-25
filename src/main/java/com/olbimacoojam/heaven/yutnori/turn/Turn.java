package com.olbimacoojam.heaven.yutnori.turn;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.Color;
import com.olbimacoojam.heaven.yutnori.participant.YutnoriParticipant;
import com.olbimacoojam.heaven.yutnori.participant.YutnoriParticipants;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import com.olbimacoojam.heaven.yutnori.turn.exception.MoveImpossibleException;
import com.olbimacoojam.heaven.yutnori.turn.exception.NotHaveYutException;
import com.olbimacoojam.heaven.yutnori.turn.exception.ThrowImpossibleException;
import com.olbimacoojam.heaven.yutnori.turn.exception.WrongUserTurnException;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.Yuts;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Turn {

    private final YutnoriParticipant yutnoriParticipant;
    private final boolean canThrow;
    private Yuts thrownYuts;

    private Turn(YutnoriParticipant yutnoriParticipant, Yuts thrownYuts, boolean canThrow) {
        this.yutnoriParticipant = yutnoriParticipant;
        this.thrownYuts = thrownYuts;
        this.canThrow = canThrow;
    }

    public Turn(YutnoriParticipant yutnoriParticipant) {
        this(yutnoriParticipant, new Yuts(), true);
    }

    public Turn saveOneThrow(User thrower, Yut yut) {
        checkUser(thrower);
        checkCanThrow();

        Yuts thrownYuts = this.thrownYuts.add(yut);
        return new Turn(yutnoriParticipant, thrownYuts, thrownYuts.isThrowAvailable());
    }

    private void checkCanThrow() {
        if (!canThrow) {
            throw new ThrowImpossibleException();
        }
    }

    private void checkUser(User user) {
        if (!yutnoriParticipant.is(user)) {
            throw new WrongUserTurnException(yutnoriParticipant.getName(), user.getName());
        }
    }

    public void checkMove(User user, Yut yut) {
        checkUser(user);
        checkCanMove();
        checkHaveYut(yut);
    }

    private void checkCanMove() {
        if (canThrow) {
            throw new MoveImpossibleException();
        }
    }

    private void checkHaveYut(Yut yut) {
        if (thrownYuts.notHave(yut)) {
            throw new NotHaveYutException(yut);
        }
    }

    public Color getTeamColor() {
        return yutnoriParticipant.getColor();
    }

    public Turn next(MoveResults moveResults, YutnoriParticipants yutnoriParticipants) {
        if (moveResults.hasCaught()) {
            return new Turn(yutnoriParticipant, thrownYuts, true);
        }

        if (yutnoriParticipants.isPlaying(yutnoriParticipant) && thrownYuts.isRemaining()) {
            return new Turn(yutnoriParticipant, thrownYuts, false);
        }

        return new Turn(yutnoriParticipants.next(yutnoriParticipant));
    }

    public User getUser() {
        return yutnoriParticipant.getParticipant();
    }

    public void removeYut(Yut yut) {
        thrownYuts = thrownYuts.remove(yut);
    }
}
