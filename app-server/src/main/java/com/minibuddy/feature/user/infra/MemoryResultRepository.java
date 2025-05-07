package com.minibuddy.feature.user.infra;

import com.minibuddy.feature.user.domain.MemoryResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryResultRepository extends JpaRepository<MemoryResult, Long> {
}
