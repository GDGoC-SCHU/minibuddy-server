package com.minibuddy.feature.ai.client;

import com.minibuddy.feature.ai.client.dto.ChatResponse;
import com.minibuddy.feature.ai.client.dto.MemoryAnswerResponse;

public interface ChatClient {

    ChatResponse normalChat(String userId, String message);

    ChatResponse memoryChat(String userId, String message);

    MemoryAnswerResponse memoryAnswer(String userId, String answer, String questionId);
}
