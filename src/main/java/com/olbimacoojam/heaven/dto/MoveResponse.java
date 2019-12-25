package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.piece.YutnoriGameResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MoveResponse {

    private MoveResultsDto moveResultsDto;
    private YutnoriGameResult yutnoriGameResult;
    private TurnResponse turnResponse;

    public MoveResponse(MoveResultsDto moveResultsDto, YutnoriGameResult yutnoriGameResult, TurnResponse turnResponse) {
        this.moveResultsDto = moveResultsDto;
        this.yutnoriGameResult = yutnoriGameResult;
        this.turnResponse = turnResponse;
    }
}
