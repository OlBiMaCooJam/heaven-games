package com.olbimacoojam.heaven.mafia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MafiaChatMessage {
    private Long userId;
    private String name;
    private String message;
}
