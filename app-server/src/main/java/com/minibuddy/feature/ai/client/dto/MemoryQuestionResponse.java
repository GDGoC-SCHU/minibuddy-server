package com.minibuddy.feature.ai.client.dto;

public record MemoryQuestionResponse(
        String reply,
        int depScore
) {
}
