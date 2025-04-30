package com.minibuddy.feature.ai.application;

import com.minibuddy.app.*;
import com.minibuddy.feature.ai.client.ChatClient;
import com.minibuddy.feature.ai.client.dto.MemoryAnswerResponse;
import com.minibuddy.feature.ai.client.dto.MemoryQuestionResponse;
import com.minibuddy.feature.ai.client.dto.NormalChatResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class ChatClientService implements ChatClient {

    @GrpcClient("chatAiService")
    private ChatAiServiceGrpc.ChatAiServiceBlockingStub serviceBlockingStub;

    // TODO Mapper 사용으로 dto 변환 자동화...?
    @Override
    public NormalChatResponse normalChat(String userId, String message) {
        ChatRequest request = ChatRequest.newBuilder()
                .setUserId(userId)
                .setMessage(message)
                .build();
        ChatResponse response = serviceBlockingStub.getChatResponse(request);

        return new NormalChatResponse(
                response.getReply(),
                response.getDepScore()
        );
    }

    @Override
    public MemoryQuestionResponse memoryChat(String userId, String message) {
        ChatRequest request = ChatRequest.newBuilder()
                .setUserId(userId)
                .setMessage(message)
                .build();
        com.minibuddy.app.MemoryQuestionResponse response = serviceBlockingStub.getMemoryQuestion(request);

        return new MemoryQuestionResponse(
                response.getQuestion(),
                response.getDepScore()
        );
    }

    @Override
    public MemoryAnswerResponse memoryAnswer(String userId, String answer, String questionId) {
        MemoryAnswerRequest request = MemoryAnswerRequest.newBuilder()
                .setUserId(userId)
                .setAnswer(answer)
                .setQuestionId(questionId)
                .build();
        MemoryAnswerResult response = serviceBlockingStub.evaluateMemoryAnswer(request);

        return new MemoryAnswerResponse(
                response.getReply(),
                response.getMciScore(),
                response.getDepScore()
        );
    }
}
