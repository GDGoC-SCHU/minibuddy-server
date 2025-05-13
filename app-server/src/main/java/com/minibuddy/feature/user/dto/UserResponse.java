package com.minibuddy.feature.user.dto;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        String name,
        LocalDate birthdate,
        List<String> keywords
) {
}
