package com.olbimacoojam.heaven.draw.application;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DrawCreateRequest {
    private Integer personCount;
    private Integer whackCount;
}
