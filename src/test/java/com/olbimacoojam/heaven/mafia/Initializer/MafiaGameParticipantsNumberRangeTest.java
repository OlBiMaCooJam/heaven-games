package com.olbimacoojam.heaven.mafia.Initializer;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.MafiaParticipant;
import com.olbimacoojam.heaven.mafia.Occupation.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MafiaGameParticipantsNumberRangeTest {

    @Test
    void 마피아게임_4명_초기세팅_테스트() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            users.add(new User());
        }
        List<MafiaParticipant> mafiaParticipants = MafiaGameParticipantsNumberRange.initialize(users);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupation() instanceof Mafia) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Doctor) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Citizen) {
                numberOfCitizen++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(1);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(2);
    }

    @Test
    void 마피아게임_5명_초기세팅_테스트() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(new User());
        }
        List<MafiaParticipant> mafiaParticipants = MafiaGameParticipantsNumberRange.initialize(users);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupation() instanceof Mafia) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Doctor) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Citizen) {
                numberOfCitizen++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(1);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(3);
    }

    @Test
    void 마피아게임_6명_초기세팅_테스트() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            users.add(new User());
        }
        List<MafiaParticipant> mafiaParticipants = MafiaGameParticipantsNumberRange.initialize(users);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupation() instanceof Mafia) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Doctor) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Police) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Citizen) {
                numberOfCitizen++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(2);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfPolice).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(2);
    }

    @Test
    void 마피아게임_7명_초기세팅_테스트() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            users.add(new User());
        }
        List<MafiaParticipant> mafiaParticipants = MafiaGameParticipantsNumberRange.initialize(users);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupation() instanceof Mafia) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Doctor) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Police) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Citizen) {
                numberOfCitizen++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(2);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfPolice).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(3);
    }

    @Test
    void 마피아게임_8명_초기세팅_테스트() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            users.add(new User());
        }
        List<MafiaParticipant> mafiaParticipants = MafiaGameParticipantsNumberRange.initialize(users);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupation() instanceof Mafia) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Doctor) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Police) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Citizen) {
                numberOfCitizen++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(2);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfPolice).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(4);
    }

    @Test
    void 마피아게임_9명_초기세팅_테스트() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            users.add(new User());
        }
        List<MafiaParticipant> mafiaParticipants = MafiaGameParticipantsNumberRange.initialize(users);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;
        int numberOfSoldier = 0;
        int numberOfDetective = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupation() instanceof Mafia) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Doctor) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Police) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Citizen) {
                numberOfCitizen++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Soldier) {
                numberOfSoldier++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Detective) {
                numberOfDetective++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(3);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfPolice).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(2);
        assertThat(numberOfDetective).isEqualTo(1);
        assertThat(numberOfSoldier).isEqualTo(1);
    }

    @Test
    void 마피아게임_10명_초기세팅_테스트() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User());
        }
        List<MafiaParticipant> mafiaParticipants = MafiaGameParticipantsNumberRange.initialize(users);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;
        int numberOfSoldier = 0;
        int numberOfDetective = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupation() instanceof Mafia) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Doctor) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Police) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Citizen) {
                numberOfCitizen++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Soldier) {
                numberOfSoldier++;
            }
            if (mafiaParticipants.get(i).getOccupation() instanceof Detective) {
                numberOfDetective++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(3);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfPolice).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(3);
        assertThat(numberOfDetective).isEqualTo(1);
        assertThat(numberOfSoldier).isEqualTo(1);
    }
}