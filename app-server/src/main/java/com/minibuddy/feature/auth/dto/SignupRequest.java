package com.minibuddy.feature.auth.dto;

import java.time.LocalDate;
import java.util.List;

public record SignupRequest(
        String uid,
        String nickname,
        LocalDate birthdate,
        List<String> keywords,
        String fcmToken
) {
}
