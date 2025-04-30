package com.minibuddy.feature.user.domain;

import com.minibuddy.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Score extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private String userId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer depScore;
    private Integer anxScore;
    private Integer strScore;
    private Integer mciScore;
}
