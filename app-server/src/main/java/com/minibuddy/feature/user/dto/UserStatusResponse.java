package com.minibuddy.feature.user.dto;

public record UserStatusResponse(
   int depScore,
   int anxScore,
   int strScore,
   int mciScore,
   int chatCount
) {
}
