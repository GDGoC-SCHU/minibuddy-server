package com.minibuddy.feature.user.dto;

import java.time.LocalDate;

public record MemoryHistory(
        LocalDate date,
        String question,
        String answer,
        String mciReason
) {
}
