package com.minibuddy.feature.user.dto;

import java.time.LocalDate;

public record EmotionFlowResponse(
        LocalDate date,
        int depScore,
        int anxScore,
        int strScore
) {
}
