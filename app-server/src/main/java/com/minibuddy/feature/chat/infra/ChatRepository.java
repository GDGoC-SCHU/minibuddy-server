package com.minibuddy.feature.chat.infra;

import com.minibuddy.feature.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
