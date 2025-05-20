package com.minibuddy.feature.user.dto;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        String nickname,
        LocalDate birthdate,
        List<String> keywords
) {
}
