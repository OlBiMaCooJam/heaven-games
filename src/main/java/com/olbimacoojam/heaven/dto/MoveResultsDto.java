package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@ToString
@Getter
public class MoveResultsDto {

    private List<MoveResultDto> moveResults;

    public MoveResultsDto(List<MoveResultDto> moveResults) {
        this.moveResults = moveResults;
    }
}
