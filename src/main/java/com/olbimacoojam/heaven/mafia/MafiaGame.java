package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaInitializerRegistry;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMaximumInitializer;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMiddleInitializer;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMinimumInitializer;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MafiaGame implements Game {
    private static final MafiaInitializerRegistry MAFIA_INITIALIZER_REGISTRY = new MafiaInitializerRegistry();
    private List<MafiaParticipant> mafiaParticipants;
    private Map<MafiaParticipant, Integer> voteResult;
    private Map<MafiaParticipant, MafiaParticipant> selectData;

    private boolean day;

    public MafiaGame() {
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMinimumInitializer());
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMiddleInitializer());
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMaximumInitializer());
        voteResult = new HashMap<>();
        selectData = new HashMap<>();
        day = true;
    }

    /**
     * 마피아 게임에 참여하고 있는 Player 들의 역할을 정하는 메서드
     */
    @Override
    public void initialize(List<User> players) {
        mafiaParticipants = MAFIA_INITIALIZER_REGISTRY.getMafiaInitializer(players.size()).initialize(players);
        voteResultInitialize();
    }

    private void voteResultInitialize() {
        for (MafiaParticipant mafiaParticipant : findAliveMafiaParticipants()) {
            voteResult.put(mafiaParticipant, 0);
        }
    }

    public boolean isEnd() {
        return true;
    }

    public MafiaResult getResult() {
        return MafiaResult.CITIZEN_WIN;
    }

    public List<MafiaParticipant> getMafiaParticipants() {
        return mafiaParticipants;
    }

    public List<MafiaParticipant> findAliveMafiaParticipants() {
        return mafiaParticipants.stream()
                .filter(mafiaParticipant -> mafiaParticipant.isAlive())
                .collect(Collectors.toList());
    }

    public MafiaParticipant findMafiaParticipantByUserId(Long userId) {
        return mafiaParticipants.stream()
                .filter(mafiaParticipant -> mafiaParticipant.getPlayer().getId().equals(userId))
                .findFirst()
                .orElseThrow(NotFoundMafiaParticipantException::new);
    }

    public boolean voteParticipant(MafiaParticipant mafiaParticipant) {
        voteResult.put(mafiaParticipant, voteResult.get(mafiaParticipant) + 1);
        int voteSum = 0;
        for (Integer voteValue : voteResult.values()) {
            voteSum += voteValue;
        }
        if (voteSum == mafiaParticipants.size()) {
            return true;
        }
        return false;
    }

    public String showVoteResult() {
        int maxVote = voteResult.values().stream()
                .max(Comparator.comparing(Integer::intValue))
                .orElseThrow(IllegalArgumentException::new);

        int maxVoteNumber = (int) voteResult.values().stream().filter(voteNumber -> voteNumber.equals(maxVote)).count();

        if (maxVoteNumber >= 2) {
            voteResult = new HashMap<>();
            return "투표결과가 과반수가 넘지 않아 무효가 되었습니다";
        }

        MafiaParticipant selectedMafiaParticipant = voteResult.keySet().stream()
                .filter(mafiaParticipant -> voteResult.get(mafiaParticipant).equals(maxVote))
                .findFirst()
                .orElseThrow(NotFoundMafiaParticipantException::new);

        selectedMafiaParticipant.dead();
        String distinction = selectedMafiaParticipant.getOccupationType().equals(OccupationType.MAFIA) ? "마피아" : "시민";

        voteResult = new HashMap<>();
        return selectedMafiaParticipant.getPlayer().getName() + "님이 죽었습니다.\n" + distinction + " 입니다.";
    }

    public String showResult() {
        day = false;

        int numberOfMafia = (int) findAliveMafiaParticipants().stream()
                .filter(mafiaParticipant -> mafiaParticipant.getOccupationType().equals(OccupationType.MAFIA))
                .count();

        if (numberOfMafia >= mafiaParticipants.size() - numberOfMafia) {
            return ("게임이 종료되었습니다.\n마피아팀의 승리입니다.");
        }

        if (numberOfMafia == 0) {
            return ("게임이 종료되었습니다.\n시민팀의 승리입니다.");
        }

        return day ? "밤이 되었습니다." : "낮이 되었습니다.";
    }

    public String showNightResult() {
        day = true;

        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append(showVoteResult())
                .append(showResult())
                .toString();
    }

    public boolean isDay() {
        return day;
    }

    public void changeDay() {
        if (this.day) {
            this.day = false;
            return;
        }
        this.day = true;
    }

    public void checkSelect(MafiaParticipant selector, MafiaParticipant selected) {
        selectData.put(selector, selected);
    }

    public String getSelectData(String mafiaParticipantName) {
        return findAliveMafiaParticipants().stream()
                .filter(mafiaParticipant -> mafiaParticipant.getPlayer().getName().equals(mafiaParticipantName))
                .findFirst()
                .orElseThrow(NotFoundMafiaParticipantException::new)
                .getOccupationType().name();
    }
}
