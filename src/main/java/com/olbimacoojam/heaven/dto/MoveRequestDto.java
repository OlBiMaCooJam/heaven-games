package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MoveRequestDto {
    private String pointName;
    private String yut;

    public MoveRequestDto(String pointName, String yut) {
        this.pointName = pointName;
        this.yut = yut;
    }
}
