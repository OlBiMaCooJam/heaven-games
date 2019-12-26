package com.olbimacoojam.heaven.yutnori.piece.moveresult;

import com.olbimacoojam.heaven.yutnori.piece.Piece;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Getter
public class MoveResults {

    private final List<MoveResult> moveResults;

    public MoveResults(List<MoveResult> moveResults) {
        this.moveResults = moveResults;
    }

    public boolean isCaught(Piece piece) {
        return moveResults.stream()
                .anyMatch(moveResult -> moveResult.canCatch(piece));
    }

    public MoveResults add(MoveResults moveResults) {
        List<MoveResult> results = new ArrayList<>();
        results.addAll(this.moveResults);
        results.addAll(moveResults.moveResults);
        return new MoveResults(results);
    }

    public boolean hasCaught() {
        return moveResults.stream()
                .anyMatch(MoveResult::areYouCaught);
    }
}
