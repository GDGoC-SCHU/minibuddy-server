package com.minibuddy.feature.user.domain;

import com.minibuddy.feature.chat.domain.Chat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User {

    @Id
    private String userId;  // firebase UID

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    private int chatCount;

    @Column(nullable = false)
    private String notificationToken;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DepressionScore> depressionScores = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MciScore> mciScores = new ArrayList<>();

    @Builder
    public User(String userId, String email, String name, int age, LocalDateTime createdAt, String notificationToken) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.age = age;
        this.createdAt = createdAt;
        this.notificationToken = notificationToken;
    }

    public void updateChatCount() {
        this.chatCount += 1;
    }

    public DepressionScore saveDepScore(int depScore, Long chatId) {
        DepressionScore depressionScore = new DepressionScore(this, depScore, chatId);
        this.getDepressionScores().add(depressionScore);
        return depressionScore;
    }
}
