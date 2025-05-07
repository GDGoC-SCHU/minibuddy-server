package com.minibuddy.feature.ai.application;

import com.minibuddy.feature.ai.client.ChatClient;
import com.minibuddy.feature.ai.client.dto.ChatResponse;
import com.minibuddy.feature.ai.client.dto.MemoryAnswerResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class FakeChatClientService implements ChatClient {
    @Override
    public ChatResponse normalChat(String userId, String message) {
        int score = (int) (Math.random() * 20);
        return new ChatResponse("normal reply", score, score, score, false);
    }

    @Override
    public ChatResponse memoryChat(String userId, String answer) {
        int score = (int) (Math.random() * 20);
        return new ChatResponse("memory question", score, score, score, true);
    }

    @Override
    public MemoryAnswerResponse memoryAnswer(String userId, String answer, String questionId) {
        int score = (int) (Math.random() * 20);
        return new MemoryAnswerResponse("memory answer", score, "reason");
    }
}
