package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.game.Game;

import java.util.List;

public class MafiaGame implements Game {
    private List<MafiaParticipant> players;

    /**
     * 마피아 게임에 참여하고 있는 Player 들의 역할을 정하는 메서드
     */
    @Override
    public void initialize(List<User> players) {
        //최소 4명
        //4~5명은 마피아 1명, 6명 이상 2명, 9명 이상 3명 - 최대 10명 제한
        //특수 직업, 마피아 랜덤 배정
        //4~5명 이면, 마피아 1, 시민 2~3, 의사1
        //6~8명 이면, 마피아 2, 시민 2~4, 의사1, 경찰1
        //9~10명 이면, 마피아 3, 시민 2~3, 의사1, 경찰1, 군인1, 사립탐정1
    }

    /**
     *
     */
    public boolean isEnd() {
        return true;
    }

    public MafiaResult getResult() {
        return MafiaResult.CITIZEN_WIN;
    }
}
