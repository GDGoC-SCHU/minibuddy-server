package com.minibuddy.feature.user.application;

import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.user.dto.UpdateFcmRequest;
import com.minibuddy.feature.user.dto.UserResponse;
import com.minibuddy.feature.user.infra.UserRepository;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse updateNotificationToken(PrincipalDetails session, UpdateFcmRequest request) {
        User currentUser = userRepository.findById(session.getUsername())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        currentUser.updateNotificationToken(request.fcmToken());

        return new UserResponse(
                currentUser.getName(),
                currentUser.getBirthday(),
                currentUser.getKeywords()
        );
    }

    public String withdrawal(PrincipalDetails session) {
        userRepository.deleteById(session.getUsername());
        return "user deleted successfully";
    }
}
