package com.olbimacoojam.heaven.game;

import com.olbimacoojam.heaven.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * 게임 전적 저장을 위한 GameResult Class
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class GameResult extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private GameKind gameKind;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User player;

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
