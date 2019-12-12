package com.olbimacoojam.heaven.mafia.Initializer;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.MafiaParticipant;
import com.olbimacoojam.heaven.mafia.Occupation.Citizen;
import com.olbimacoojam.heaven.mafia.Occupation.Doctor;
import com.olbimacoojam.heaven.mafia.Occupation.Mafia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Mafia4to5Initializer implements MafiaInitializer {
    @Override
    public List<MafiaParticipant> initialize(List<User> users) {
        Collections.shuffle(users);
        List<MafiaParticipant> mafiaParticipants = new ArrayList<>();
        mafiaParticipants.add(new MafiaParticipant(users.get(0), new Mafia()));
        mafiaParticipants.add(new MafiaParticipant(users.get(1), new Doctor()));
        IntStream.range(2, users.size())
                .forEach(i -> mafiaParticipants.add(new MafiaParticipant(users.get(i), new Citizen())));
        return mafiaParticipants;
    }
}
