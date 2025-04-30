package com.minibuddy.feature.chat.domain;

import com.minibuddy.feature.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@Getter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 사용자가 보낸 채팅 메세지

    private boolean isMemoryQuestion;   // true이면 기억력 질문에 대한 사용자의 응답
    private boolean isUser; // true이면 일반 유저의 응답

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Chat(User user, String content, boolean isMemoryQuestion, boolean isUser) {
        this.user = user;
        this.content = content;
        this.isMemoryQuestion = isMemoryQuestion;
        this.isUser = isUser;
    }
}
