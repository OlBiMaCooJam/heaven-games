package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * temporary class
 */

@Getter
@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode
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
