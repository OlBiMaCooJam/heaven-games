package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class YutnoriParticipantResponse {

    private UserResponseDto participant;
    private Color color;

    public YutnoriParticipantResponse(UserResponseDto participant, Color color) {
        this.participant = participant;
        this.color = color;
    }
}
