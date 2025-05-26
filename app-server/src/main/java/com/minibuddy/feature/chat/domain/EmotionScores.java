package com.minibuddy.feature.chat.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmotionScores {
    private Integer depressionScore;
    private Integer anxietyScore;
    private Integer stressScore;
}
