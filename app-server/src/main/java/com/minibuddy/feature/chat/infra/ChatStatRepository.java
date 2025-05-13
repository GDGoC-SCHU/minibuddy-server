package com.minibuddy.feature.chat.infra;

import com.minibuddy.feature.chat.domain.ChatStat;
import com.minibuddy.feature.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChatStatRepository extends JpaRepository<ChatStat, Long> {
    Optional<ChatStat> findByUserAndDate(User user, LocalDate date);

    List<ChatStat> findByUser(User user);

    List<ChatStat> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
