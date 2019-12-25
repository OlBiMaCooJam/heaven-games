package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MoveResponse {

    private MoveResultsDto moveResultsDto;
    private Boolean finish;
    private TurnResponse turnResponse;

    public MoveResponse(MoveResultsDto moveResultsDto, Boolean finish, TurnResponse turnResponse) {
        this.moveResultsDto = moveResultsDto;
        this.finish = finish;
        this.turnResponse = turnResponse;
    }
}
