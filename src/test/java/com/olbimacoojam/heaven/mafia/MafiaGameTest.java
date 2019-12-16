package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MafiaGameTest {
    private MafiaGame mafiaGame = new MafiaGame();

    @Test
    void 마피아게임_4명_초기세팅_테스트() {
        List<MafiaParticipant> mafiaParticipants = createMafiaParticipants(4);

        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.MAFIA)) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DOCTOR)) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.CITIZEN)) {
                numberOfCitizen++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(1);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(2);
    }

    @Test
    void 마피아게임_5명_초기세팅_테스트() {
        List<MafiaParticipant> mafiaParticipants = createMafiaParticipants(5);

        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.MAFIA)) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DOCTOR)) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.CITIZEN)) {
                numberOfCitizen++;
            }
        }
        assertThat(numberOfMafia).isEqualTo(1);
        assertThat(numberOfDoctor).isEqualTo(1);
        assertThat(numberOfCitizen).isEqualTo(3);
    }

    @Test
    void 마피아게임_6명_초기세팅_테스트() {
        List<MafiaParticipant> mafiaParticipants = createMafiaParticipants(6);

        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.MAFIA)) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DOCTOR)) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.POLICE)) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.CITIZEN)) {
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
        List<MafiaParticipant> mafiaParticipants = createMafiaParticipants(7);

        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.MAFIA)) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DOCTOR)) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.POLICE)) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.CITIZEN)) {
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
        List<MafiaParticipant> mafiaParticipants = createMafiaParticipants(8);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.MAFIA)) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DOCTOR)) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.POLICE)) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.CITIZEN)) {
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
        List<MafiaParticipant> mafiaParticipants = createMafiaParticipants(9);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;
        int numberOfSoldier = 0;
        int numberOfDetective = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.MAFIA)) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DOCTOR)) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.POLICE)) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.CITIZEN)) {
                numberOfCitizen++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.SOLDIER)) {
                numberOfSoldier++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DETECTIVE)) {
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
        List<MafiaParticipant> mafiaParticipants = createMafiaParticipants(10);
        int numberOfMafia = 0;
        int numberOfDoctor = 0;
        int numberOfPolice = 0;
        int numberOfCitizen = 0;
        int numberOfSoldier = 0;
        int numberOfDetective = 0;

        for (int i = 0; i < mafiaParticipants.size(); i++) {
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.MAFIA)) {
                numberOfMafia++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DOCTOR)) {
                numberOfDoctor++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.POLICE)) {
                numberOfPolice++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.CITIZEN)) {
                numberOfCitizen++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.SOLDIER)) {
                numberOfSoldier++;
            }
            if (mafiaParticipants.get(i).getOccupationType().equals(OccupationType.DETECTIVE)) {
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

    private List<MafiaParticipant> createMafiaParticipants(int number) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            users.add(new User());
        }

        mafiaGame.initialize(users);
        return mafiaGame.getMafiaParticipants();
    }
}