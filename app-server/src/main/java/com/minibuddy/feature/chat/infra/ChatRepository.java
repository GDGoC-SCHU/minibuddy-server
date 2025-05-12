package com.minibuddy.feature.chat.infra;

import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.feature.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByUser(User user);
}
