package com.minibuddy.feature.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FcmUpdateRequest {
    @NotNull
    private String fcmToken;
}
