package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResult;
import com.olbimacoojam.heaven.yutnori.piece.moveresult.MoveResults;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
public class MoveResultDtos {
    private List<MoveResultDto> moverResultDtos;

    public MoveResultDtos(MoveResults moveResults) {
        List<MoveResult> moverResults = moveResults.getMoveResults();
        moverResultDtos = moverResults.stream()
                .map(moveResult -> new MoveResultDto(moveResult.getColor(), moveResult.getRoute()))
                .collect(Collectors.toList());
    }

    public List<MoveResultDto> getMoverResultDtos() {
        return moverResultDtos;
    }
}
