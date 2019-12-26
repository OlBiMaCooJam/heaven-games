package com.olbimacoojam.heaven.mafia;

import com.olbimacoojam.heaven.mafia.Occupation.Occupation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MafiaOccupationMessage {
    private Long userId;
    private Occupation occupation;
}
