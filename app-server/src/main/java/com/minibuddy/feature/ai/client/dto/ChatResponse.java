package com.minibuddy.feature.ai.client.dto;

public record ChatResponse(
        String reply,
        int depScore,
        int anxScore,
        int strScore,
        boolean isMemoryQuestion
) {
}
