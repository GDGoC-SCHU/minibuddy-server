package com.minibuddy.feature.chat.application;

import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.feature.chat.dto.AiMemoryQuestionReply;
import com.minibuddy.feature.chat.dto.AiReply;
import com.minibuddy.feature.user.domain.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScoreUpdater {

    @Transactional(propagation = Propagation.MANDATORY)
    public AiReply updateWithReply(User user, Chat chat, String reply, HttpServletResponse servletResponse) {
        user.getScore().updateDepAnxStrScore(
                chat.getEmotionScores().getDepressionScore(),
                chat.getEmotionScores().getAnxietyScore(),
                chat.getEmotionScores().getStressScore());
        if (chat.getIsMemoryQuestion()) {
            servletResponse.setHeader("X-Chat-Response-Type", "memory-question");
            return new AiMemoryQuestionReply(reply, chat.getChatId());
        } else {
            servletResponse.setHeader("X-Chat-Response-Type", "normal");
            return new AiReply(reply);
        }
    }
}
