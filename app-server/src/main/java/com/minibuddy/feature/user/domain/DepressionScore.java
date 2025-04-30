package com.minibuddy.feature.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "depression_score")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DepressionScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depressionScoreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer depressionScore;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime assessedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "depression_risk_level", nullable = false)
    private RiskLevel depressionRiskLevel;

    private Long chatId;    // chatId 만 저장

    @Builder
    public DepressionScore(User user, Integer depressionScore, Long chatId) {
        this.user = user;
        this.depressionScore = depressionScore;
        this.chatId = chatId;
    }

    @PrePersist
    @PreUpdate
    public void calculateDepressionRiskLevel() {
        this.depressionRiskLevel = RiskLevel.determine(this.depressionScore);
    }

    @AllArgsConstructor
    public enum RiskLevel{
        // 일단 임의로 해둠 ㅋ
        LOW(0, 5),
        MEDIUM(6, 10),
        HIGH(11, Integer.MAX_VALUE);

        private final int min;
        private final int max;

        public static RiskLevel determine(int depressionScore) {
            return Arrays.stream(values())
                    .filter(level -> depressionScore >= level.min && depressionScore <= level.max)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }
}
