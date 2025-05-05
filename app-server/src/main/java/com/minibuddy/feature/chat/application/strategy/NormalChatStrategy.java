package com.minibuddy.feature.chat.application.strategy;

import com.minibuddy.feature.ai.client.ChatClient;
import com.minibuddy.feature.chat.application.ChatStrategy;
import com.minibuddy.feature.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NormalChatStrategy implements ChatStrategy {

    private final ChatClient client;

    @Override
    public boolean supports(int chatCount) {
        return chatCount % 10 != 0;
    }

    @Override
    public Object process(User user, String chat) {
        return client.normalChat(user.getUserId(), chat);
    }
}
