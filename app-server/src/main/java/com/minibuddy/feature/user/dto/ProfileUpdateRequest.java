package com.minibuddy.feature.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
public class ProfileUpdateRequest {
    private String nickname;
    private LocalDate birthdate;
    private List<String> keywords;
}
