package com.minibuddy.feature.user.api;

import com.minibuddy.feature.user.application.UserService;
import com.minibuddy.feature.user.dto.UpdateFcmRequest;
import com.minibuddy.feature.user.dto.UserResponse;
import com.minibuddy.global.response.SuccessResponse;
import com.minibuddy.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestBody UpdateFcmRequest request
    ) {
        UserResponse response = userService.updateNotificationToken(session, request);
        return SuccessResponse.ok(response);
    }
}











