package com.minibuddy.feature.user.infra;

import com.minibuddy.feature.user.domain.MemoryResult;
import com.minibuddy.feature.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoryResultRepository extends JpaRepository<MemoryResult, Long> {

    List<MemoryResult> findByUser(User user);
}
