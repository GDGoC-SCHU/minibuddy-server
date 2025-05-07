package com.minibuddy.feature.user.api;

import com.minibuddy.feature.user.application.UserService;
import com.minibuddy.feature.user.dto.FcmUpdateRequest;
import com.minibuddy.feature.user.dto.ProfileUpdateRequest;
import com.minibuddy.feature.user.dto.UserResponse;
import com.minibuddy.global.response.SuccessResponse;
import com.minibuddy.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

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
}











