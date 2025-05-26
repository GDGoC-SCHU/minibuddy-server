package com.minibuddy.feature.chat.domain;

import com.minibuddy.feature.chat.domain.enums.EmotionType;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@Getter
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 사용자가 보낸 채팅 메세지

    @Column(nullable = false)
    private Boolean isMemoryQuestion;

    @Column(nullable = false)
    private Boolean isUser;

    @Embedded
    private EmotionScores emotionScores;

    @Enumerated(EnumType.STRING)
    private EmotionType dominantEmotion;

//    @OneToOne(mappedBy = "answerChat", cascade = CascadeType.ALL)
//    private MemoryResult memoryResult;


    @Builder
    public Chat(User user, String content, Boolean isMemoryQuestion, Boolean isUser, EmotionScores emotionScores) {
        this.user = user;
        this.content = content;
        this.isMemoryQuestion = isMemoryQuestion;
        this.isUser = isUser;
        this.emotionScores = emotionScores;
        this.dominantEmotion = EmotionType.dominantEmotionType(emotionScores);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
