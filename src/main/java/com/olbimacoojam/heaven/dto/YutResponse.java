package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.yut.Yut;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class YutResponse {

    private Yut yut;
    private TurnResponse turnResponse;

    public YutResponse(Yut yut, TurnResponse turnResponse) {
        this.yut = yut;
        this.turnResponse = turnResponse;
    }
}
