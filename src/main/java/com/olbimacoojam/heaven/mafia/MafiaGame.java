package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaInitializerRegistry;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMaximumInitializer;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMiddleInitializer;
import com.olbimacoojam.heaven.mafia.Initializer.MafiaMinimumInitializer;
import com.olbimacoojam.heaven.mafia.Occupation.OccupationType;

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
    private boolean isStart;

    public MafiaGame() {
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMinimumInitializer());
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMiddleInitializer());
        MAFIA_INITIALIZER_REGISTRY.addMafiaInitializer(new MafiaMaximumInitializer());
        voteResult = new HashMap<>();
        selectData = new HashMap<>();
        day = true;
        isStart = false;
    }

    /**
     * 마피아 게임에 참여하고 있는 Player 들의 역할을 정하는 메서드
     */
    @Override
    public void initialize(List<User> players) {
        mafiaParticipants = MAFIA_INITIALIZER_REGISTRY.getMafiaInitializer(players.size()).initialize(players);
        voteResultInitialize();
        isStart = true;
    }

    @Override
    public boolean isStart() {
        return isStart;
    }

    private void voteResultInitialize() {
        for (MafiaParticipant mafiaParticipant : findAliveMafiaParticipants()) {
            voteResult.put(mafiaParticipant, 0);
        }
        selectData = new HashMap<>();
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
        if (voteSum == findAliveMafiaParticipants().size()) {
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
            return "투표결과가 과반수가 넘지 않아 무효가 되었습니다\n";
        }

        MafiaParticipant selectedMafiaParticipant = voteResult.keySet().stream()
                .filter(mafiaParticipant -> voteResult.get(mafiaParticipant).equals(maxVote))
                .findFirst()
                .orElseThrow(NotFoundMafiaParticipantException::new);

        boolean isDead = selectedMafiaParticipant.dead();

        if (!isDead) {
            return "죽은 사람이 없습니다.\n";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(selectedMafiaParticipant.getPlayer().getName() + "님이 죽었습니다.\n");

        voteResult.remove(selectedMafiaParticipant);

        if (day) {
            stringBuilder.append(
                    selectedMafiaParticipant.getOccupationType().equals(OccupationType.MAFIA) ?
                            "마피아입니다.\n" : "시민입니다.\n");
        }
        return stringBuilder.toString();
    }

    public String showResult() {
        int numberOfMafia = (int) findAliveMafiaParticipants().stream()
                .filter(mafiaParticipant -> mafiaParticipant.getOccupationType().equals(OccupationType.MAFIA))
                .count();

        voteResultInitialize();

        if (numberOfMafia >= findAliveMafiaParticipants().size() - numberOfMafia) {
            isStart = false;
            return ("게임이 종료되었습니다.\n마피아팀의 승리입니다.");
        }

        if (numberOfMafia == 0) {
            isStart = false;
            return ("게임이 종료되었습니다.\n시민팀의 승리입니다.");
        }

        changeDay();
        return day ? "낮이 되었습니다." : "밤이 되었습니다.";
    }

    public String showNightResult() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append(showVoteResult())
                .append(showResult())
                .toString();
    }

    public String showDayResult() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append(showVoteResult())
                .append(showResult())
                .toString();
    }

    public boolean isDay() {
        return day;
    }

    private void changeDay() {
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

    public boolean isAllPerform() {
        int numberOfCompetence = (int) findAliveMafiaParticipants().stream()
                .filter(mafiaParticipant -> !mafiaParticipant.getOccupationType().equals(OccupationType.CITIZEN)
                        && !mafiaParticipant.getOccupationType().equals(OccupationType.SOLDIER))
                .count();

        return numberOfCompetence == selectData.size();
    }
}
