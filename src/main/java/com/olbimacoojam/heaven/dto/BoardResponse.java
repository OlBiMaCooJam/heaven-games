package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class BoardResponse {

    List<PieceResponse> pieces;

    public BoardResponse(List<PieceResponse> pieces) {
        this.pieces = pieces;
    }
}
