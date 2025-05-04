package com.minibuddy.feature.auth.api;

import com.minibuddy.feature.auth.application.AuthService;
import com.minibuddy.feature.auth.dto.SignupRequest;
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
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SuccessResponse<?> signup(@RequestBody SignupRequest request) {
        UserResponse response = authService.register(request);
        return SuccessResponse.ok(response);
    }

    @PostMapping("/logout")
    public SuccessResponse<?> logout(@AuthenticationPrincipal PrincipalDetails session) {
        String response = authService.logout(session);
        return SuccessResponse.ok(response);
    }
}
