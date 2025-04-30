package com.minibuddy.feature.chat.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EmotionScores {
    private Integer depressionScore;
    private Integer anxietyScore;
    private Integer stressScore;
}
