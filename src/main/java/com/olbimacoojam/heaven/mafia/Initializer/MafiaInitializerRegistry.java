package com.olbimacoojam.heaven.mafia.Initializer;

import java.util.ArrayList;
import java.util.List;

public class MafiaInitializerRegistry {
    private final List<MafiaInitializer> mafiaInitializers = new ArrayList<>();

    public void addMafiaInitializer(MafiaInitializer mafiaInitializer) {
        mafiaInitializers.add(mafiaInitializer);
    }

    public MafiaInitializer getMafiaInitializer(int numberOfParticipants) {
        return mafiaInitializers.stream()
                .filter(mafiaInitializer -> mafiaInitializer.matches(numberOfParticipants))
                .findFirst()
                .orElseThrow(InvalidNumberOfMafiaParticipantsException::new);
    }

}
