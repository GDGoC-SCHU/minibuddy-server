package com.minibuddy.feature.chat.application;

import com.minibuddy.feature.ai.client.dto.ChatResponse;
import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.feature.chat.domain.ChatStat;
import com.minibuddy.feature.chat.domain.EmotionScores;
import com.minibuddy.feature.chat.dto.AiReply;
import com.minibuddy.feature.chat.infra.ChatRepository;
import com.minibuddy.feature.chat.infra.ChatStatRepository;
import com.minibuddy.feature.user.domain.ScoreHistory;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.user.infra.ScoreHistoryRepository;
import com.minibuddy.feature.user.infra.UserRepository;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.security.PrincipalDetails;
import jakarta.servlet.http.HttpServletResponse;
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
    private final ChatRepository chatRepository;
    private final ScoreHistoryRepository scoreHistoryRepository;

    @Transactional
    public AiReply processChat(PrincipalDetails session, String message, HttpServletResponse servletResponse) {
        User user = userRepository.findById(session.getUsername())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        ChatStat chatStat = getChatStat(user);
        Integer chatCount = chatStat.updateTotalCount();

        ChatStrategy strategy = strategyFactory.getStrategy(chatCount);

        ChatResponse aiResponse = strategy.process(user, message);
        Chat chat = Chat.builder()
                .user(user)
                .content(message)
                .isMemoryQuestion(false)
                .isUser(true)
                .emotionScores(new EmotionScores(aiResponse.depScore(), aiResponse.anxScore(), aiResponse.strScore()))
                .build();
        Chat savedChat = chatRepository.save(chat);
        if (aiResponse.isMemoryQuestion()) {
            saveMemoryQuestion(user, aiResponse.reply(), chat.getEmotionScores());
        }

        ScoreHistory scoreHistory = getScoreHistory(user, aiResponse);
        scoreHistory.updateScoreHistory(aiResponse.depScore(), aiResponse.anxScore(), aiResponse.strScore());

        return scoreUpdater.updateWithReply(user, savedChat, aiResponse.reply(), aiResponse.isMemoryQuestion(), servletResponse);
    }

    private void saveMemoryQuestion(User user, String reply, EmotionScores scores) {
        Chat chat = Chat.builder()
                .user(user)
                .content(reply)
                .isMemoryQuestion(true)
                .isUser(false)
                .emotionScores(scores)
                .build();
        chatRepository.save(chat);
    }

    private ScoreHistory getScoreHistory(User user, ChatResponse aiResponse) {
        return scoreHistoryRepository.findByUserAndDate(user, LocalDate.now())
                .orElseGet(() -> {
                            ScoreHistory build = ScoreHistory.builder()
                                    .user(user)
                                    .date(LocalDate.now())
                                    .depScore(aiResponse.depScore())
                                    .anxScore(aiResponse.anxScore())
                                    .strScore(aiResponse.strScore())
                                    .build();
                            user.addScoreHistory(build);
                            return build;
                        }
                );
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
