package com.minibuddy.feature.chat.api;

import com.minibuddy.feature.chat.application.ChatService;
import com.minibuddy.feature.chat.dto.AiReply;
import com.minibuddy.feature.chat.dto.NormalRequest;
import com.minibuddy.global.security.PrincipalDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public AiReply normalChat(
            @AuthenticationPrincipal PrincipalDetails session,
            @RequestBody NormalRequest request,
            HttpServletResponse servletResponse
    ) {
        return chatService.processChat(session, request.getChat(), servletResponse);
    }
}
