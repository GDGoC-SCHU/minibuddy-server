package com.minibuddy.feature.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mci_score")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MciScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long chatId;    // chatId 만 저장

    @Column(nullable = false)
    private int mciScore;


}
