package com.minibuddy.feature.user.domain;

import com.minibuddy.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Score extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private String userId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer depScore;
    private Integer anxScore;
    private Integer strScore;
    private Integer mciScore;

    @Builder
    public Score(String userId, User user) {
        this.userId = userId;
        this.user = user;
        this.depScore = 0;
        this.anxScore = 0;
        this.strScore = 0;
        this.mciScore = 0;
    }

    public void updateDepAnxStrScore(int depScore, int anxScore, int strScore) {
        this.depScore = depScore;
        this.anxScore = anxScore;
        this.strScore = strScore;
    }

    public void updateDepScore(int depScore) {
        this.depScore = depScore;
    }

    public void updateAnxScore(int anxScore) {
        this.anxScore = anxScore;
    }

    public void updateStrScore(int strScore) {
        this.strScore = strScore;
    }

    public void updateMciScore(int mciScore) {
        this.mciScore = mciScore;
    }
}
