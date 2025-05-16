package com.minibuddy.feature.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FcmUpdateRequest {
    @NotNull
    @JsonProperty("fcm-token")
    private String fcmToken;
}
