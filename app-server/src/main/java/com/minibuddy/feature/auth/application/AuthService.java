package com.minibuddy.feature.auth.application;

import com.minibuddy.feature.user.domain.Score;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.auth.dto.SignupRequest;
import com.minibuddy.feature.user.dto.UserResponse;
import com.minibuddy.feature.user.infra.UserRepository;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse register(SignupRequest request) {
        User user = User.builder()
                .userId(request.uid())
                .nickname(request.nickname())
                .birthday(request.birthdate())
                .keywords(request.keywords())
                .notificationToken(request.fcmToken())
                .build();
        Score score = Score.builder()
                .userId(request.uid())
                .user(user)
                .build();
        user.addScore(score);
        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getNickname(),
                saved.getBirthday(),
                saved.getKeywords()
        );
    }

    @Transactional
    public String logout(PrincipalDetails session) {
        User user = userRepository.findById(session.getUsername())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        user.updateNotificationToken(null);
        SecurityContextHolder.clearContext();
        return "logout";
    }
}
