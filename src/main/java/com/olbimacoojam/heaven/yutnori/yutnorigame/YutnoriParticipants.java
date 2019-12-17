package com.olbimacoojam.heaven.yutnori.yutnorigame;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.yutnori.yutnorigame.exception.NotExistParticipantException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class YutnoriParticipants {
    private final List<YutnoriParticipant> yutnoriParticipants;

    public YutnoriParticipants(List<User> players) {
        List<Color> colors = Arrays.asList(Color.values());
        yutnoriParticipants = IntStream.range(0, players.size())
                .mapToObj(index -> new YutnoriParticipant(players.get(index), colors.get(index)))
                .collect(Collectors.toList());
    }

    public Stream<YutnoriParticipant> stream() {
        return yutnoriParticipants.stream();
    }

    public YutnoriParticipant getFirst() {
        return yutnoriParticipants.get(0);
    }

    public YutnoriParticipant next(YutnoriParticipant yutnoriParticipant) {
        int nextIndex = getNextParticipantIndex(yutnoriParticipant);
        return yutnoriParticipants.get(nextIndex);
    }

    private int getNextParticipantIndex(YutnoriParticipant currentParticipant) {
        int size = yutnoriParticipants.size();
        int currentIndex = findIndexOf(currentParticipant);
        return (currentIndex + 1) % size;
    }

    private int findIndexOf(YutnoriParticipant participant) {
        int index = yutnoriParticipants.indexOf(participant);

        if (index == -1) {
            throw new NotExistParticipantException(participant.getName());
        }

        return index;
    }

    public YutnoriParticipant get(int index) {
        return yutnoriParticipants.get(index);
    }
}
