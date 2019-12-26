package com.olbimacoojam.heaven.draw.application;

import com.olbimacoojam.heaven.draw.domain.Lots;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DrawResponse {
    private Lots lots;

    public DrawResponse(Lots lots) {
        this.lots = lots;
    }
}
