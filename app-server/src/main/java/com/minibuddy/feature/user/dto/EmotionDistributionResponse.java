package com.minibuddy.feature.user.dto;

public record EmotionDistributionResponse(
        int totalCount,
        int normal,
        int dep,
        int anx,
        int str
) {
}
