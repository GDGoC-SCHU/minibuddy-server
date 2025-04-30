package com.minibuddy.feature.chat.domain;

import com.minibuddy.feature.user.domain.User;
import com.minibuddy.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "chat_stat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatStat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatStatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "DATE")
    private LocalDate date; // 기준 날짜(일 단위)

    private Integer totalCount;
    private Integer normalCount;
    private Integer depressionCount;
    private Integer anxietyCount;
    private Integer stressCount;
}
