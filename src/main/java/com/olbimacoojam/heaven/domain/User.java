package com.olbimacoojam.heaven.domain;

import com.olbimacoojam.heaven.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

/**
 * temporary class
 */

@Getter
@Entity
@ToString
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(kakaoId, user.kakaoId) &&
                Objects.equals(name, user.name) &&
                Objects.equals(refreshToken, user.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kakaoId, name, refreshToken);
    }
}
