package com.minibuddy.feature.user.application;

import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.feature.chat.domain.ChatStat;
import com.minibuddy.feature.chat.domain.enums.EmotionType;
import com.minibuddy.feature.chat.infra.ChatRepository;
import com.minibuddy.feature.chat.infra.ChatStatRepository;
import com.minibuddy.feature.user.domain.Score;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.user.dto.*;
import com.minibuddy.feature.user.infra.UserRepository;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ChatStatRepository chatStatRepository;
    private final ChatRepository chatRepository;

    public UserResponse updateNotificationToken(PrincipalDetails session, FcmUpdateRequest request) {
        User currentUser = getCurrentUser(session);
        currentUser.updateNotificationToken(request.getFcmToken());

        return new UserResponse(
                currentUser.getName(),
                currentUser.getBirthday(),
                currentUser.getKeywords()
        );
    }

    public String withdrawal(PrincipalDetails session) {
        userRepository.deleteById(session.getUsername());
        return "user deleted successfully";
    }

    public UserResponse updateProfile(PrincipalDetails session, ProfileUpdateRequest request) {
        User currentUser = getCurrentUser(session);
        currentUser.updateProfile(request.getNickname(), request.getBirthdate(), request.getKeywords());

        return new UserResponse(
                currentUser.getName(),
                currentUser.getBirthday(),
                currentUser.getKeywords()
        );
    }

    @Transactional(readOnly = true)
    public UserResponse profile(PrincipalDetails session) {
        User currentUser = getCurrentUser(session);

        return new UserResponse(
                currentUser.getName(),
                currentUser.getBirthday(),
                currentUser.getKeywords()
        );
    }

    @Transactional(readOnly = true)
    public UserStatusResponse status(PrincipalDetails session) {
        User currentUser = getCurrentUser(session);
        Score score = currentUser.getScore();
        List<ChatStat> chatStats = chatStatRepository.findByUser(currentUser);

        int chatCount = 0;
        for (ChatStat stat : chatStats) {
            chatCount += stat.getTotalCount();
        }

        return new UserStatusResponse(
                score.getDepScore(),
                score.getAnxScore(),
                score.getStrScore(),
                score.getMciScore(),
                chatCount
        );
    }

    @Transactional(readOnly = true)
    public List<EmotionFlowResponse> emotionFlow(PrincipalDetails session) {
        User currentUser = getCurrentUser(session);
        List<ChatStat> chatStats = chatStatRepository.findByUser(currentUser);

        return chatStats.stream()
                .sorted(Comparator.comparing(ChatStat::getDate))
                .limit(14)
                .map(it -> new EmotionFlowResponse(
                        it.getDate(),
                        it.getDepressionCount(),
                        it.getAnxietyCount(),
                        it.getStressCount()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public EmotionDistributionResponse emotionDistribution(PrincipalDetails session) {
        User currentUser = getCurrentUser(session);
        List<Chat> chats = chatRepository.findAllByUser(currentUser).stream()
                .filter(Chat::getIsUser)
                .toList();

        Map<EmotionType, Long> emotionCounts = chats.stream()
                .map(Chat::getDominantEmotion)
                .collect(Collectors.groupingBy(
                        emotion -> emotion,
                        Collectors.counting()
                ));

        return new EmotionDistributionResponse(
                chats.size(),
                emotionCounts.getOrDefault(EmotionType.NORMAL, 0L).intValue(),
                emotionCounts.getOrDefault(EmotionType.DEPRESSION, 0L).intValue(),
                emotionCounts.getOrDefault(EmotionType.ANXIETY, 0L).intValue(),
                emotionCounts.getOrDefault(EmotionType.STRESS, 0L).intValue()
        );
    }

    private User getCurrentUser(PrincipalDetails session) {
        return userRepository.findById(session.getUsername())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
