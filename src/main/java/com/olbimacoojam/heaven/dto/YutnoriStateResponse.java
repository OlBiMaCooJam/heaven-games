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

    public YutnoriStateResponse(List<YutnoriParticipantResponse> yutnoriParticipants,
                                BoardResponse boardResponse) {
        this.yutnoriParticipants = yutnoriParticipants;
        this.boardResponse = boardResponse;
    }
}
