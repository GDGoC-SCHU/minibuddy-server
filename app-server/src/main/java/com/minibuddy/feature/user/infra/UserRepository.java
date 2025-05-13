package com.minibuddy.feature.user.infra;

import com.minibuddy.feature.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
