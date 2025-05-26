package com.minibuddy.feature.ai.application;

import com.minibuddy.app.*;
import com.minibuddy.feature.ai.client.ChatClient;
import com.minibuddy.feature.ai.client.dto.MemoryAnswerResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ChatClientService implements ChatClient {

    @GrpcClient("chatAiService")
    private ChatAiServiceGrpc.ChatAiServiceBlockingStub serviceBlockingStub;

    // TODO Mapper 사용으로 dto 변환 자동화...?
    @Override
    public com.minibuddy.feature.ai.client.dto.ChatResponse normalChat(String userId, String message) {
        ChatRequest request = ChatRequest.newBuilder()
                .setUserId(userId)
                .setMessage(message)
                .build();
        ChatResponse response = serviceBlockingStub.getChatResponse(request);

        return new com.minibuddy.feature.ai.client.dto.ChatResponse(
                response.getReply(),
                response.getDepScore(),
                response.getAnxScore(),
                response.getStrScore(),
                false
        );
    }

    @Override
    public com.minibuddy.feature.ai.client.dto.ChatResponse memoryChat(String userId, String message) {
        ChatRequest request = ChatRequest.newBuilder()
                .setUserId(userId)
                .setMessage(message)
                .build();
        com.minibuddy.app.MemoryQuestionResponse response = serviceBlockingStub.getMemoryQuestion(request);

        return new com.minibuddy.feature.ai.client.dto.ChatResponse(
                response.getQuestion(),
                response.getDepScore(),
                response.getAnxScore(),
                response.getStrScore(),
                true
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
                response.getMciReason()
        );
    }
}
