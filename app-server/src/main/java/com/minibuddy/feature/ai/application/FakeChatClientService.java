package com.minibuddy.feature.ai.application;

import com.minibuddy.feature.ai.client.ChatClient;
import com.minibuddy.feature.ai.client.dto.MemoryAnswerResponse;
import com.minibuddy.feature.ai.client.dto.MemoryQuestionResponse;
import com.minibuddy.feature.ai.client.dto.NormalChatResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class FakeChatClientService implements ChatClient {
    @Override
    public NormalChatResponse normalChat(String userId, String message) {
        int score = (int) (Math.random() * 20);
        return new NormalChatResponse("normal reply", score, score, score);
    }

    @Override
    public MemoryQuestionResponse memoryChat(String userId, String answer) {
        int score = (int) (Math.random() * 20);
        return new MemoryQuestionResponse("memory question", score);
    }

    @Override
    public MemoryAnswerResponse memoryAnswer(String userId, String answer, String questionId) {
        int score = (int) (Math.random() * 20);
        return new MemoryAnswerResponse("memory answer", score, score, "reason");
    }
}
