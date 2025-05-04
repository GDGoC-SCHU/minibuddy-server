package com.minibuddy.feature.chat.application;

import com.minibuddy.feature.user.domain.User;

public interface ChatStrategy {
    boolean supports(int chatCount);
    Object process(User user, String chat);
}
