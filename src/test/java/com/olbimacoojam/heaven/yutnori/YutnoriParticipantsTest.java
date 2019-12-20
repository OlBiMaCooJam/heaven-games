package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class YutnoriParticipantsTest {

    @Test
    void next_test() {
        User user1 = new User(1L, "name1", "token1");
        User user2 = new User(2L, "name2", "token2");

        YutnoriParticipants yutnoriParticipants = new YutnoriParticipants(Arrays.asList(user1, user2));

        YutnoriParticipant firstParticipant = yutnoriParticipants.getFirst();
        YutnoriParticipant secondParticipant = yutnoriParticipants.get(1);

        assertThat(yutnoriParticipants.next(firstParticipant)).isEqualTo(secondParticipant);
        assertThat(yutnoriParticipants.next(secondParticipant)).isEqualTo(firstParticipant);
    }

}