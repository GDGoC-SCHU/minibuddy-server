package com.minibuddy.feature.user.dto;

import java.time.LocalDate;

public record UserEmotionFlowResponse(
        LocalDate date,
        int depScore,
        int anxScore,
        int strScore
) {
}
