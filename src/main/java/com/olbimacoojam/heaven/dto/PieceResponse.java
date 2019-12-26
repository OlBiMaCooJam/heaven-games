package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.Color;
import com.olbimacoojam.heaven.yutnori.point.PointName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PieceResponse {

    private Color color;
    private PointName pointName;

    public PieceResponse(Color color, PointName pointName) {
        this.color = color;
        this.pointName = pointName;
    }

}
