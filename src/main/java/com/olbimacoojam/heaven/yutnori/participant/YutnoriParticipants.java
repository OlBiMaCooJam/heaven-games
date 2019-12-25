package com.olbimacoojam.heaven.yutnori.participant;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.Color;
import com.olbimacoojam.heaven.yutnori.exception.NoSuchColorPlayingException;
import com.olbimacoojam.heaven.yutnori.exception.NotExistParticipantException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class YutnoriParticipants {

    private final List<YutnoriParticipant> allYutnoriParticipants;
    private final List<YutnoriParticipant> finishedYutnoriParticipants;

    public YutnoriParticipants(List<YutnoriParticipant> allYutnoriParticipants, List<YutnoriParticipant> finishedYutnoriParticipants) {
        this.allYutnoriParticipants = allYutnoriParticipants;
        this.finishedYutnoriParticipants = finishedYutnoriParticipants;
    }

    private YutnoriParticipants(List<YutnoriParticipant> yutnoriParticipants) {
        this(yutnoriParticipants, new ArrayList<>());
    }

    public static YutnoriParticipants of(List<User> players) {
        return IntStream.range(0, players.size())
                .mapToObj(i -> new YutnoriParticipant(players.get(i), Color.get(i)))
                .collect(Collectors.collectingAndThen(Collectors.toList(), YutnoriParticipants::new));
    }

    public YutnoriParticipant getFirst() {
        return allYutnoriParticipants.get(0);
    }

    public YutnoriParticipant next(YutnoriParticipant yutnoriParticipant) {
        int nextIndex = getNextParticipantIndex(yutnoriParticipant);
        YutnoriParticipant nextYutnoriParticipant = allYutnoriParticipants.get(nextIndex);
        return finishedYutnoriParticipants.contains(nextYutnoriParticipant) ? next(nextYutnoriParticipant) : nextYutnoriParticipant;
    }

    private int getNextParticipantIndex(YutnoriParticipant currentParticipant) {
        int size = allYutnoriParticipants.size();
        int currentIndex = findIndexOf(currentParticipant);
        return (currentIndex + 1) % size;
    }

    private int findIndexOf(YutnoriParticipant participant) {
        int index = allYutnoriParticipants.indexOf(participant);

        if (index == -1) {
            throw new NotExistParticipantException(participant.getName());
        }

        return index;
    }

    public YutnoriParticipant get(int index) {
        return allYutnoriParticipants.get(index);
    }

    public void finish(Color color) {
        YutnoriParticipant finishedYutnoriParticipant = allYutnoriParticipants.stream()
                .filter(yutnoriParticipant -> yutnoriParticipant.isColor(color))
                .findFirst()
                .orElseThrow(NoSuchColorPlayingException::new);

        finishedYutnoriParticipants.add(finishedYutnoriParticipant);
    }

    public boolean isPlaying(YutnoriParticipant yutnoriParticipant) {
        return !finishedYutnoriParticipants.contains(yutnoriParticipant);
    }

    public List<Color> getColors() {
        return allYutnoriParticipants.stream()
                .map(YutnoriParticipant::getColor)
                .collect(Collectors.toList());
    }

    public List<YutnoriParticipant> getYutnoriParticipants() {
        return Collections.unmodifiableList(allYutnoriParticipants);
    }
}
