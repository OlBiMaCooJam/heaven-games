package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.piece.YutnoriGameResult;
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
    private YutnoriGameResult yutnoriGameResult;

    public MoveResultDtos(MoveResults moveResults, YutnoriGameResult yutnoriGameResult) {
        List<MoveResult> moverResults = moveResults.getMoveResults();
        this.moverResultDtos = moverResults.stream()
                .map(moveResult -> new MoveResultDto(moveResult.getColor(), moveResult.getRoute()))
                .collect(Collectors.toList());
        this.yutnoriGameResult = yutnoriGameResult;
    }

    public List<MoveResultDto> getMoverResultDtos() {
        return moverResultDtos;
    }
}
