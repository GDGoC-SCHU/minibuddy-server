package com.minibuddy.domain.user.application;

import com.minibuddy.feature.chat.domain.Chat;
import com.minibuddy.feature.chat.domain.EmotionScores;
import com.minibuddy.feature.chat.infra.ChatRepository;
import com.minibuddy.feature.chat.infra.ChatStatRepository;
import com.minibuddy.feature.user.application.UserService;
import com.minibuddy.feature.user.domain.User;
import com.minibuddy.feature.user.dto.EmotionDistributionResponse;
import com.minibuddy.feature.user.dto.ProfileUpdateRequest;
import com.minibuddy.feature.user.dto.UserResponse;
import com.minibuddy.feature.user.infra.ScoreHistoryRepository;
import com.minibuddy.feature.user.infra.UserRepository;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.security.PrincipalDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChatStatRepository chatStatRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private ScoreHistoryRepository scoreHistoryRepository;

    @InjectMocks
    private UserService userService;

    private final PrincipalDetails mockPrincipal = new PrincipalDetails("testUser", "test@minibuddy.com");
    private final User mockUser = new User("userUid", "user", LocalDate.of(2001, 5, 7), List.of("keyword1", "keyword2"), "token");

    @Test
    void 프로필을_업데이트_할_수_있다() {
        // Given
        ProfileUpdateRequest request = new ProfileUpdateRequest("new", LocalDate.now(), List.of("newKeyword1"));
        User mockUser = User.builder()
                .name("old")
                .birthday(LocalDate.of(2000, 1, 1))
                .keywords(List.of("oldKeyword1"))
                .build();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(mockUser));

        // When
        UserResponse response = userService.updateProfile(mockPrincipal, request);

        // Then
        assertThat(response.name()).isEqualTo("new");
        assertThat(response.keywords()).containsExactly("newKeyword1");
        verify(userRepository).findById("testUser");
    }

    @Test
    void 존재하지_않는_사용자_조회시_예외가_발생한다() {
        // Given
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.profile(mockPrincipal))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(UserErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 감정_분포_로직은_총_대화_수와_우세_감정_대화의_수를_반환한다() {
        // Given
        User mockUser = User.builder()
                .name("username")
                .birthday(LocalDate.of(2000, 1, 1))
                .keywords(List.of("keyword1"))
                .build();
        Chat chat1 = Chat.builder()
                .user(mockUser)
                .content("message1")
                .isMemoryQuestion(false)
                .isUser(true)
                .emotionScores(new EmotionScores(3, 1, 1))
                .build();
        Chat chat2 = Chat.builder()
                .user(mockUser)
                .content("message2")
                .isMemoryQuestion(false)
                .isUser(true)
                .emotionScores(new EmotionScores(1, 1, 3))
                .build();
        List<Chat> testChats = List.of(chat1, chat2);

        when(chatRepository.findAllByUser(any())).thenReturn(testChats);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(mockUser));

        // When
        EmotionDistributionResponse response = userService.emotionDistribution(mockPrincipal);

        // Then
        assertThat(response.totalCount()).isEqualTo(2);
        assertThat(response.normal()).isEqualTo(0);
        assertThat(response.dep()).isEqualTo(1);
        assertThat(response.anx()).isEqualTo(0);
        assertThat(response.str()).isEqualTo(1);
    }
}
