package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.yut.Yuts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TurnResponse {

    private YutnoriParticipantResponse yutnoriParticipant;
    private Yuts thrownYuts;
    private Boolean canThrow;

    public TurnResponse(YutnoriParticipantResponse yutnoriParticipant, Yuts thrownYuts, Boolean canThrow) {
        this.yutnoriParticipant = yutnoriParticipant;
        this.thrownYuts = thrownYuts;
        this.canThrow = canThrow;
    }
}
