package com.olbimacoojam.heaven.mafia.Initializer;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.MafiaParticipant;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class MafiaMaximumInitializer implements MafiaInitializer {
    private static final int MINIMAL_NUMBER = 9;
    private static final int MAXIMAL_NUMBER = 10;

    @Override
    public boolean matches(int numberOfParticipants) {
        return numberOfParticipants >= MINIMAL_NUMBER && numberOfParticipants <= MAXIMAL_NUMBER;
    }

    @Override
    public List<MafiaParticipant> initialize(List<User> users) {
        Collections.shuffle(users);
        List<MafiaParticipant> mafiaParticipants = new ArrayList<>();
        IntStream.range(0, 3)
                .forEach(i -> mafiaParticipants.add(new MafiaParticipant(users.get(i), OccupationType.MAFIA)));
        mafiaParticipants.add(new MafiaParticipant(users.get(3), OccupationType.DOCTOR));
        mafiaParticipants.add(new MafiaParticipant(users.get(4), OccupationType.POLICE));
        mafiaParticipants.add(new MafiaParticipant(users.get(5), OccupationType.DETECTIVE));
        mafiaParticipants.add(new MafiaParticipant(users.get(6), OccupationType.SOLDIER));
        IntStream.range(7, users.size())
                .forEach(i -> mafiaParticipants.add(new MafiaParticipant(users.get(i), OccupationType.CITIZEN)));
        return mafiaParticipants;
    }
}
