package com.minibuddy.feature.chat.application;

import com.minibuddy.feature.ai.client.dto.ChatResponse;
import com.minibuddy.feature.user.domain.User;

public interface ChatStrategy {
    boolean supports(int chatCount);
    ChatResponse process(User user, String chat);
}
