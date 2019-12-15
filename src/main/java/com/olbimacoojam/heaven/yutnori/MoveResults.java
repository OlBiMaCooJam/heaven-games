package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.point.Point;

import java.util.List;
import java.util.Objects;

public class MoveResults {
    private final List<MoveResult> moveResults;

    public MoveResults(List<MoveResult> moveResults) {
        this.moveResults = moveResults;
    }

    public Point findDestination() {
        return moveResults.get(0).findDestination();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveResults that = (MoveResults) o;
        return Objects.equals(moveResults, that.moveResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveResults);
    }
}
