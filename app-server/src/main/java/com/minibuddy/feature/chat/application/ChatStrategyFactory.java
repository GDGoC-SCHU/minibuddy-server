package com.minibuddy.feature.chat.application;

import com.minibuddy.global.error.code.ChatErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatStrategyFactory {
    private final List<ChatStrategy> strategies;

    public ChatStrategy getStrategy(int chatCount) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(chatCount))
                .findFirst()
                .orElseThrow(() -> new CustomException(ChatErrorCode.NO_SUCH_CHAT_STRATEGY));
    }
}
