package com.minibuddy.feature.chat.application;

import com.minibuddy.feature.ai.client.dto.MemoryQuestionResponse;
import com.minibuddy.feature.ai.client.dto.NormalChatResponse;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.global.error.code.ChatErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScoreUpdater {

    @Transactional(propagation = Propagation.MANDATORY)
    public String updateWithReply(User user, Object aiResponse) {
        if (aiResponse instanceof NormalChatResponse) {
            return handleNormalResponse(user, (NormalChatResponse) aiResponse);
        } else if (aiResponse instanceof MemoryQuestionResponse) {
            return handleMemoryQuestionResponse(user, (MemoryQuestionResponse) aiResponse);
        } else {
            throw new CustomException(ChatErrorCode.UNSUPPORTED_RESPONSE_TYPE);
        }
    }

    private String handleMemoryQuestionResponse(User user, MemoryQuestionResponse aiResponse) {
        user.getScore().updateDepScore(aiResponse.depScore());
        return aiResponse.question();
    }

    private String handleNormalResponse(User user, NormalChatResponse aiResponse) {
        user.getScore().updateDepAnxStrScore(aiResponse.depScore(), aiResponse.anxScore(), aiResponse.strScore());
        return aiResponse.reply();
    }
}
