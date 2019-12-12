package com.olbimacoojam.heaven.mafia.Initializer;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.MafiaParticipant;

import java.util.List;

public interface MafiaInitializer {
    boolean matches(int numberOfParticipants);

    List<MafiaParticipant> initialize(List<User> users);
}