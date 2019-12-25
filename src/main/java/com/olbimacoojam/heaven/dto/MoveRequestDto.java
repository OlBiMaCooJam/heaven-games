package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.point.PointName;
import com.olbimacoojam.heaven.yutnori.yut.Yut;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MoveRequestDto {

    private PointName sourcePoint;
    private Yut yut;

    public MoveRequestDto(PointName sourcePoint, Yut yut) {
        this.sourcePoint = sourcePoint;
        this.yut = yut;
    }
}
