package com.olbimacoojam.heaven.game;

import com.olbimacoojam.heaven.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

/**
 * temporary class
 */

@Getter
@Entity
@ToString
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long kakaoId;

    @Column
    private String name;

    public User(Long kakaoId, String name) {
        this.kakaoId = kakaoId;
        this.name = name;
    }
}
