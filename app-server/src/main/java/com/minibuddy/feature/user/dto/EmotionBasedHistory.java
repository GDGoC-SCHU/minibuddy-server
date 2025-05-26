package com.minibuddy.feature.user.dto;

import java.time.LocalDate;

public record EmotionBasedHistory(
        LocalDate date,
        String message
) {
}
