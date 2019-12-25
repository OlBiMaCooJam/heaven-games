package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class YutnoriStateResponse {

    private List<YutnoriParticipantResponse> yutnoriParticipants;
    private BoardResponse boardResponse;
    private TurnResponse turn;

    public YutnoriStateResponse(List<YutnoriParticipantResponse> yutnoriParticipants,
                                BoardResponse boardResponse,
                                TurnResponse turn) {
        this.yutnoriParticipants = yutnoriParticipants;
        this.boardResponse = boardResponse;
        this.turn = turn;
    }
}
