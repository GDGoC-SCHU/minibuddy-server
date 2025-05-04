package com.minibuddy.feature.chat.application;

import com.minibuddy.feature.chat.domain.ChatStat;
import com.minibuddy.feature.chat.infra.ChatStatRepository;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.user.infra.UserRepository;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatStatRepository chatStatRepository;
    private final ChatStrategyFactory strategyFactory;
    private final ScoreUpdater scoreUpdater;

    @Transactional
    public String processChat(PrincipalDetails session, String message) {
        User user = userRepository.findById(session.getUsername())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        ChatStat chatStat = getChatStat(user);
        Integer chatCount = chatStat.updateTotalCount();

        // 전략 선택
        ChatStrategy strategy = strategyFactory.getStrategy(chatCount);
        // grpc 호출
        Object aiResponse = strategy.process(user, message);

        return scoreUpdater.updateWithReply(user, aiResponse);
    }

    private ChatStat getChatStat(User user) {
        // TODO ChatStat 일별로 어떻게 관리할 것인지
        return chatStatRepository.findByUserAndDate(user, LocalDate.now())
                .orElseGet(() -> chatStatRepository.save(ChatStat.builder()
                        .user(user)
                        .date(LocalDate.now())
                        .build())
                );
    }
}
