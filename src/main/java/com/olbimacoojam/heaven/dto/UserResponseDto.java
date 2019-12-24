package com.olbimacoojam.heaven.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private Long kakaoId;
    private String name;
    private String refreshToken;

    public UserResponseDto(Long kakaoId, String name, String refreshToken) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", kakaoId=" + kakaoId +
                ", name='" + name + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
