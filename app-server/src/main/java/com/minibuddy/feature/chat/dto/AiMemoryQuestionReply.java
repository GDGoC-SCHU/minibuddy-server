package com.minibuddy.feature.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AiMemoryQuestionReply extends AiReply{
    private Long questionId;

    public AiMemoryQuestionReply(String reply, Long questionId) {
        super(reply);
        this.questionId = questionId;
    }
}
