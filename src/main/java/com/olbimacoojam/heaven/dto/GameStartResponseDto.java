package com.olbimacoojam.heaven.dto;

import com.olbimacoojam.heaven.yutnori.point.PointName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class GameStartResponseDto {
    private Long id;
    private String userName;
    private String color;
    private List<String> pieceLocations;

    public GameStartResponseDto(Long id, String userName, String color) {
        this.id = id;
        this.userName = userName;
        this.color = color;
        this.pieceLocations = Arrays.asList(PointName.STANDBY.name(), PointName.STANDBY.name(), PointName.STANDBY.name(), PointName.STANDBY.name());
    }

    public boolean isName(String userName) {
        return this.userName.equals(userName);
    }
}
