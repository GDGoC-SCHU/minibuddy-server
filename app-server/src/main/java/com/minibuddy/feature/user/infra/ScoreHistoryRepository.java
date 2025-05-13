package com.minibuddy.feature.user.infra;

import com.minibuddy.feature.user.domain.ScoreHistory;
import com.minibuddy.feature.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, Long> {
    Optional<ScoreHistory> findByUserAndDate(User user, LocalDate today);

    List<ScoreHistory> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
