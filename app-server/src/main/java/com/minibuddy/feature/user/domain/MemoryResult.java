package com.minibuddy.feature.user.domain;

import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "memory_result")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoryResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoryResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "answer_chat_id")
    private Chat answerChat;

    @OneToOne
    @JoinColumn(name = "question_chat_id")
    private Chat questionChat;

    private Integer mciScore;

    @Lob
    private String mciReason;

    private Integer depScore;
}
