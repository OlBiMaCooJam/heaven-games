package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Entity
@ToString
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true)
    private Long kakaoId;

    @Column
    private String name;

    @Column
    private String refreshToken;

    public User(Long kakaoId, String name, String refreshToken) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.refreshToken = refreshToken;
    }
}
