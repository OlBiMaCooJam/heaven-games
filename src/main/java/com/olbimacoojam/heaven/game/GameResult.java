package com.olbimacoojam.heaven.game;

import com.olbimacoojam.heaven.BaseEntity;
import com.olbimacoojam.heaven.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * 게임 전적 저장을 위한 GameResult Class
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class GameResult extends BaseEntity {

    @OnDelete(action = OnDeleteAction.CASCADE)
    @Enumerated(EnumType.STRING)
    private GameKind gameKind;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User player;

    @Embedded
    private Result result;

    public GameResult(GameKind gameKind, User player, Result result) {
        this.gameKind = gameKind;
        this.player = player;
        this.result = result;
    }

    public Integer getTotalScore() {
        if (result.isScoreType(gameKind)) {
            return result.getCount() * gameKind.getScorePerCount();
        }

        throw new InvalidGameTypeException();
    }
}
