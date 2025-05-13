package com.minibuddy.feature.user.api;

import com.minibuddy.feature.user.application.UserHistoryService;
import com.minibuddy.feature.user.application.UserService;
import com.minibuddy.feature.user.dto.*;
import com.minibuddy.feature.user.dto.EmotionBasedHistory;
import com.minibuddy.global.response.SuccessResponse;
import com.minibuddy.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserHistoryService userHistoryService;

    @PostMapping("/delete")
    public SuccessResponse<String> userWithdraw(@AuthenticationPrincipal PrincipalDetails session) {
        return SuccessResponse.ok(userService.withdrawal(session));
    }

    @PostMapping("/fcm-update")
    public SuccessResponse<UserResponse> fcmTokenUpdate(
            @AuthenticationPrincipal PrincipalDetails session,
            @RequestBody FcmUpdateRequest request
    ) {
        UserResponse response = userService.updateNotificationToken(session, request);
        return SuccessResponse.ok(response);
    }

    @PatchMapping("/profile")
    public SuccessResponse<UserResponse> updateProfile(
            @AuthenticationPrincipal PrincipalDetails session,
            @RequestBody ProfileUpdateRequest request
    ) {
        return SuccessResponse.ok(userService.updateProfile(session, request));
    }

    @GetMapping("/profile")
    public SuccessResponse<UserResponse> getProfile(
            @AuthenticationPrincipal PrincipalDetails session
    ) {
        return SuccessResponse.ok(userService.profile(session));
    }

    @GetMapping("/status")
    public SuccessResponse<UserStatusResponse> getStatus(
            @AuthenticationPrincipal PrincipalDetails session
    ) {
        return SuccessResponse.ok(userService.status(session));
    }

    @GetMapping("/emotion/flow")
    public SuccessResponse<List<EmotionFlowResponse>> getMonthlyEmotionFlow(
            @AuthenticationPrincipal PrincipalDetails session,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return SuccessResponse.ok(userService.getMonthlyEmotionFlow(session, year, month));   // TODO month
    }

    @GetMapping("/emotion/distribution")
    public SuccessResponse<EmotionDistributionResponse> getEmotionDistribution(
            @AuthenticationPrincipal PrincipalDetails session
    ) {
        return SuccessResponse.ok(userService.emotionDistribution(session));
    }

    @GetMapping("/history")
    public SuccessResponse<List<EmotionBasedHistory>> getDepressionHistory(
            @AuthenticationPrincipal PrincipalDetails session,
            @RequestParam EmotionTypeRequest type
    ) {
        return SuccessResponse.ok(userHistoryService.emotionHistory(session, type));
    }

    @GetMapping("/history/memory")
    public SuccessResponse<List<MemoryHistory>> getMemoryHistory(
            @AuthenticationPrincipal PrincipalDetails session
    ) {
        return SuccessResponse.ok(userHistoryService.memoryHistory(session));
    }
}











