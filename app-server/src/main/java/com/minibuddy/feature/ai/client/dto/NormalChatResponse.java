package com.minibuddy.feature.ai.client.dto;

public record NormalChatResponse(
        String reply,
        int depScore,
        int anxScore,
        int strScore
) {
}
