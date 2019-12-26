package com.olbimacoojam.heaven.mafia.Initializer;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.MafiaParticipant;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class    MafiaMinimumInitializer implements MafiaInitializer {
    private static final int MINIMAL_NUMBER = 2;
    private static final int MAXIMAL_NUMBER = 5;

    @Override
    public boolean matches(int numberOfParticipants) {
        return numberOfParticipants >= MINIMAL_NUMBER && numberOfParticipants <= MAXIMAL_NUMBER;
    }

    @Override
    public List<MafiaParticipant> initialize(List<User> users) {
        Collections.shuffle(users);
        List<MafiaParticipant> mafiaParticipants = new ArrayList<>();
        mafiaParticipants.add(new MafiaParticipant(users.get(0), OccupationType.MAFIA));
        mafiaParticipants.add(new MafiaParticipant(users.get(1), OccupationType.DOCTOR));
        IntStream.range(2, users.size())
                .forEach(i -> mafiaParticipants.add(new MafiaParticipant(users.get(i), OccupationType.CITIZEN)));
        return mafiaParticipants;
    }
}
