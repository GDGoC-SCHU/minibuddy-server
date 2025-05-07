package com.minibuddy.feature.user.domain;

import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.feature.chat.domain.ChatStat;
import com.minibuddy.global.converter.StringListConverter;
import com.minibuddy.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

    @Id
    private String userId;  // firebase UID

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Convert(converter = StringListConverter.class)
    private List<String> keywords;

    private String notificationToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Score score;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChatStat> chatStats = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ScoreHistory> scoreHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MemoryResult> memoryResults = new ArrayList<>();

    public void addScore(Score score) {
        this.score = score;
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
        chat.setUser(this);
    }

    // 편의 메서드 추가
    public void addScoreHistory(ScoreHistory history) {
        this.scoreHistories.add(history);
        history.setUser(this);
    }

    @Builder
    public User(String userId, String name, LocalDate birthday, List<String> keywords, String notificationToken) {
        this.userId = userId;
        this.name = name;
        this.birthday = birthday;
        this.keywords = keywords;
        this.notificationToken = notificationToken;
    }

    public void updateNotificationToken(String newNotificationToken) {
        this.notificationToken = newNotificationToken;
    }

    public void updateProfile(String nickname, LocalDate birthdate, List<String> keywords) {
        this.name = nickname;
        this.birthday = birthdate;
        this.keywords = keywords;
    }
}
