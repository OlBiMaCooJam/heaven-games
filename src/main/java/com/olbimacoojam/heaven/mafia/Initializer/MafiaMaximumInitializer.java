package com.olbimacoojam.heaven.mafia.Initializer;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.MafiaParticipant;
import com.olbimacoojam.heaven.mafia.Occupation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class MafiaMaximumInitializer implements MafiaInitializer {
    @Override
    public List<MafiaParticipant> initialize(List<User> users) {
        Collections.shuffle(users);
        List<MafiaParticipant> mafiaParticipants = new ArrayList<>();
        mafiaParticipants.add(new MafiaParticipant(users.get(0), new Mafia()));
        mafiaParticipants.add(new MafiaParticipant(users.get(1), new Mafia()));
        mafiaParticipants.add(new MafiaParticipant(users.get(2), new Mafia()));
        mafiaParticipants.add(new MafiaParticipant(users.get(3), new Doctor()));
        mafiaParticipants.add(new MafiaParticipant(users.get(4), new Police()));
        mafiaParticipants.add(new MafiaParticipant(users.get(5), new Detective()));
        mafiaParticipants.add(new MafiaParticipant(users.get(6), new Soldier()));
        IntStream.range(7, users.size())
                .forEach(i -> mafiaParticipants.add(new MafiaParticipant(users.get(i), new Citizen())));
        return mafiaParticipants;
    }
}
