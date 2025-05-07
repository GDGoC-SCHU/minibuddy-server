package com.minibuddy.feature.ai.client.dto;

public record MemoryAnswerResponse(
        String reply,
        int mciScore,
        String mciReason
) {
}
