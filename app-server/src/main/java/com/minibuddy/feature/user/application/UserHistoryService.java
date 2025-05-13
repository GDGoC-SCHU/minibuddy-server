package com.minibuddy.feature.user.application;

import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.feature.chat.domain.enums.EmotionType;
import com.minibuddy.feature.chat.infra.ChatRepository;
import com.minibuddy.feature.user.domain.MemoryResult;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.user.dto.EmotionBasedHistory;
import com.minibuddy.feature.user.dto.EmotionTypeRequest;
import com.minibuddy.feature.user.dto.MemoryHistory;
import com.minibuddy.feature.user.infra.MemoryResultRepository;
import com.minibuddy.feature.user.infra.UserRepository;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserHistoryService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MemoryResultRepository memoryResultRepository;

    public List<EmotionBasedHistory> emotionHistory(PrincipalDetails session, EmotionTypeRequest request) {
        User currentUser = getCurrentUser(session);
        List<Chat> chats = chatRepository.findAllByUser(currentUser);

        return chats.stream()
                .filter(Chat::getIsUser)
                .filter(it -> it.getDominantEmotion().equals(EmotionType.valueOf(request.name())))
                .limit(10)
                .map(it -> new EmotionBasedHistory(
                        it.getCreatedAt().toLocalDate(),
                        it.getContent()
                )).toList();
    }

    public List<MemoryHistory> memoryHistory(PrincipalDetails session) {
        User currentUser = getCurrentUser(session);
        List<MemoryResult> memoryResults = memoryResultRepository.findByUser(currentUser);

        return memoryResults.stream()
                .limit(10)
                .map(it -> new MemoryHistory(
                        it.getCreatedAt().toLocalDate(),
                        it.getQuestionChat().getContent(),
                        it.getAnswerChat().getContent(),
                        it.getMciReason()
                )).toList();
    }

    private User getCurrentUser(PrincipalDetails session) {
        return userRepository.findById(session.getUsername())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
