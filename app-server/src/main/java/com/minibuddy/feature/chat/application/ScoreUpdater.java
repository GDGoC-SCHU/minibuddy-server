package com.minibuddy.feature.chat.application;

import com.minibuddy.feature.chat.domain.EmotionScores;
import com.minibuddy.feature.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScoreUpdater {

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateMciScore(User user, Integer mciScore) {
        user.getScore().updateMciScore(mciScore);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateEmotionScores(User user, EmotionScores scores) {
        user.getScore().updateDepAnxStrScore(
                scores.getDepressionScore(),
                scores.getAnxietyScore(),
                scores.getStressScore());
    }
}
