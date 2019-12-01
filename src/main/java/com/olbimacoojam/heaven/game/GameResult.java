package com.olbimacoojam.heaven.game;

import com.olbimacoojam.heaven.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 게임 전적 저장을 위한 GameResult Class
 * TODO: Validation이 필요할까?
 * 만약 지뢰찾기 게임인데, result에 맞춘 문제수가 들어있다거나..
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class GameResult extends BaseEntity {

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private GameKinds gameKinds; // 지뢰, 마피아, 노래 맞추기 등등 게임 종류

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User player; // 해당 게임 결과의 주인

    private Result result; // 승, 패, 맞춘 문제 수

    public Integer getTotalScore() {
        return result.getTotalScore(gameKinds);
    }
}
