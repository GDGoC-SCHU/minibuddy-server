package com.minibuddy.feature.user.domain;

import com.minibuddy.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "score_history",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "date"},
                name = "uk_scorehistory_user_date"
        ))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "DATE")
    private LocalDate date; // 기준 날짜

    // 하루 평균 우울, 불안, 스트레스 점수
    private Integer depScore;
    private Integer anxScore;
    private Integer strScore;

    @Builder
    public ScoreHistory(User user, LocalDate date, Integer depScore, Integer anxScore, Integer strScore) {
        this.user = user;
        this.date = date;
        this.depScore = depScore;
        this.anxScore = anxScore;
        this.strScore = strScore;
    }

    public void updateScoreHistory(Integer depScore, Integer anxScore, Integer strScore) {
        this.depScore = (this.depScore + depScore) / 2;
        this.anxScore = (this.anxScore + anxScore) / 2;
        this.strScore = (this.strScore + strScore) / 2;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
