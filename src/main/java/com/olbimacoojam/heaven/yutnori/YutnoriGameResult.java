package com.olbimacoojam.heaven.yutnori.piece;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class YutnoriGameResult {

    private String message;
    private List<String> winners;

    public YutnoriGameResult(String message, List<String> winners) {
        this.message = message;
        this.winners = winners;
    }
}
