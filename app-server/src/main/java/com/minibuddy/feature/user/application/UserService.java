package com.minibuddy.feature.user.application;

import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.user.dto.FcmUpdateRequest;
import com.minibuddy.feature.user.dto.ProfileUpdateRequest;
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
    public UserResponse updateNotificationToken(PrincipalDetails session, FcmUpdateRequest request) {
        User currentUser = getCurrentUser(session);
        currentUser.updateNotificationToken(request.getFcmToken());

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

    public UserResponse updateProfile(PrincipalDetails session, ProfileUpdateRequest request) {
        User currentUser = getCurrentUser(session);
        currentUser.updateProfile(request.getNickname(), request.getBirthdate(), request.getKeywords());

        return new UserResponse(
                currentUser.getName(),
                currentUser.getBirthday(),
                currentUser.getKeywords()
        );
    }

    public UserResponse profile(PrincipalDetails session) {
        User currentUser = getCurrentUser(session);

        return new UserResponse(
                currentUser.getName(),
                currentUser.getBirthday(),
                currentUser.getKeywords()
        );
    }

    private User getCurrentUser(PrincipalDetails session) {
        return userRepository.findById(session.getUsername())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
