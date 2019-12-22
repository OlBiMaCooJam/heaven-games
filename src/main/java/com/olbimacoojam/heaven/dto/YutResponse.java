package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class YutResponse {
    private String yut;

    public YutResponse(String yut) {
        this.yut = yut;
    }
}
