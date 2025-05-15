package com.minibuddy.feature.chat.application;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.minibuddy.feature.ai.client.ChatClient;
import com.minibuddy.feature.ai.client.dto.ChatResponse;
import com.minibuddy.feature.ai.client.dto.MemoryAnswerResponse;
import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.feature.chat.domain.ChatStat;
import com.minibuddy.feature.chat.domain.EmotionScores;
import com.minibuddy.feature.chat.dto.AiMemoryQuestionReply;
import com.minibuddy.feature.chat.dto.AiReply;
import com.minibuddy.feature.chat.dto.MemoryRequest;
import com.minibuddy.feature.chat.infra.ChatRepository;
import com.minibuddy.feature.chat.infra.ChatStatRepository;
import com.minibuddy.feature.notification.NotificationService;
import com.minibuddy.feature.user.domain.MemoryResult;
import com.minibuddy.feature.user.domain.ScoreHistory;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.user.infra.MemoryResultRepository;
import com.minibuddy.feature.user.infra.ScoreHistoryRepository;
import com.minibuddy.feature.user.infra.UserRepository;
import com.minibuddy.global.error.code.ChatErrorCode;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.security.PrincipalDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatClient chatClient;
    private final UserRepository userRepository;
    private final ChatStatRepository chatStatRepository;
    private final ChatStrategyFactory strategyFactory;
    private final ScoreUpdater scoreUpdater;
    private final ChatRepository chatRepository;
    private final ScoreHistoryRepository scoreHistoryRepository;
    private final MemoryResultRepository memoryResultRepository;
    private final NotificationService notificationService;

    @Qualifier("notificationExecutor")
    private final Executor taskExecutor;

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

        chatStat.updateStatCount(savedChat.getDominantEmotion());
        sendNoti(user, savedChat.getEmotionScores());

        ScoreHistory scoreHistory = getScoreHistory(user, savedChat.getEmotionScores());
        scoreHistory.updateScoreHistory(aiResponse.depScore(), aiResponse.anxScore(), aiResponse.strScore());

        scoreUpdater.updateEmotionScores(user, savedChat.getEmotionScores());

        if (aiResponse.isMemoryQuestion()) {
            Long questionId = saveMemoryQuestionWithId(user, aiResponse.reply(), chat.getEmotionScores());
            servletResponse.setHeader("x-chat-type", "memory-question");
            return new AiMemoryQuestionReply(aiResponse.reply(), questionId);
        }
        servletResponse.setHeader("x-chat-type", "normal");
        return new AiReply(aiResponse.reply());
    }

    @Transactional
    public AiReply processMemoryQuestion(PrincipalDetails session, MemoryRequest request, HttpServletResponse servletResponse) {
        User user = userRepository.findById(session.getUsername())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        ChatStat chatStat = getChatStat(user);
        chatStat.updateTotalCount();

        MemoryAnswerResponse aiResponse = chatClient.memoryAnswer(user.getUserId(), request.getChat(), request.getQuestionId().toString());
        Chat chat = Chat.builder()
                .user(user)
                .content(request.getChat())
                .isMemoryQuestion(true)
                .isUser(true)
                .emotionScores(new EmotionScores(0, 0, 0))  // TODO memoryQuestion에 대한 답변일 경우에는 감정 점수 어케 설정해야할지
                .build();
        Chat savedChat = chatRepository.save(chat);

        memoryResultRepository.save(MemoryResult.builder()
                .user(user)
                .answerChat(savedChat)
                .questionChat(chatRepository.findById(request.getQuestionId())
                        .orElseThrow(() -> new CustomException(ChatErrorCode.NOT_FOUND)))
                .mciScore(aiResponse.mciScore())
                .mciReason(aiResponse.mciReason())
                .build());

        scoreUpdater.updateMciScore(user, aiResponse.mciScore());

        servletResponse.setHeader("x-chat-type", "memory-question");
        return new AiReply(aiResponse.reply());
    }

    private Long saveMemoryQuestionWithId(User user, String reply, EmotionScores scores) {
        Chat chat = Chat.builder()
                .user(user)
                .content(reply)
                .isMemoryQuestion(true)
                .isUser(false)
                .emotionScores(scores)
                .build();
        return chatRepository.save(chat).getChatId();
    }

    private ScoreHistory getScoreHistory(User user, EmotionScores scores) {
        return scoreHistoryRepository.findByUserAndDate(user, LocalDate.now())
                .orElseGet(() -> {
                            ScoreHistory build = ScoreHistory.builder()
                                    .user(user)
                                    .date(LocalDate.now())
                                    .depScore(scores.getDepressionScore())
                                    .anxScore(scores.getAnxietyScore())
                                    .strScore(scores.getStressScore())
                                    .build();
                            user.addScoreHistory(build);
                            return build;
                        }
                );
    }

    private ChatStat getChatStat(User user) {
        // TODO ChatStat등 일별로 관리되는 히스토리들 어떻게 관리할 것인지
        return chatStatRepository.findByUserAndDate(user, LocalDate.now())
                .orElseGet(() -> chatStatRepository.save(ChatStat.builder()
                        .user(user)
                        .date(LocalDate.now())
                        .build())
                );
    }

    private void sendNoti(User user, EmotionScores scores) {
        if (scores.getDepressionScore() >= 21 ||
                scores.getAnxietyScore() >= 21 ||
                scores.getStressScore() >= 21) {
            ApiFuture<String> future = notificationService.send(
                    user.getNotificationToken(),
                    "Warning",
                    "Take a moment to check your status."
                    );

            ApiFutures.addCallback(future, new ApiFutureCallback<>() {
                @Override
                public void onSuccess(String messageId) {
                    log.info("Notification sent successfully: {}", messageId);
                }

                @Override
                public void onFailure(Throwable t) {
                    log.error("Notification delivery failed", t);
                }
            }, taskExecutor);
        }
    }

}
