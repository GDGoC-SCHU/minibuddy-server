package com.minibuddy.feature.chat.domain;

import com.minibuddy.feature.chat.domain.enums.EmotionType;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "chat_stat",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "date"},
                name = "uk_chatstat_user_date"
        ))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatStat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatStatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "DATE")
    private LocalDate date; // 기준 날짜(일 단위)

    private Integer totalCount;
    private Integer normalCount;
    private Integer depressionCount;
    private Integer anxietyCount;
    private Integer stressCount;

    @Builder
    public ChatStat(User user, LocalDate date) {
        this.user = user;
        this.date = date;
        this.totalCount = 0;
        this.normalCount = 0;
        this.depressionCount = 0;
        this.anxietyCount = 0;
        this.stressCount = 0;
    }

    public Integer updateTotalCount() {
        return ++this.totalCount;
    }

    public void updateStatCount(EmotionType dominantEmotion) {
        switch (dominantEmotion) {
            case DEPRESSION -> this.depressionCount++;
            case ANXIETY -> this.anxietyCount++;
            case STRESS -> this.stressCount++;
            case NORMAL -> this.normalCount++;
        }
    }
}
