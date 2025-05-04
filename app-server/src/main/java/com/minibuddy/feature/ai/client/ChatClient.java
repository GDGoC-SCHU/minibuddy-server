package com.minibuddy.feature.ai.client;

import com.minibuddy.feature.ai.client.dto.MemoryQuestionResponse;
import com.minibuddy.feature.ai.client.dto.NormalChatResponse;
import com.minibuddy.feature.ai.client.dto.MemoryAnswerResponse;

public interface ChatClient {

    NormalChatResponse normalChat(String userId, String message);

    MemoryQuestionResponse memoryChat(String userId, String message);

    MemoryAnswerResponse memoryAnswer(String userId, String answer, String questionId);
}
