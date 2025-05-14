import { Injectable } from '@nestjs/common';
import { ChatRequest, ChatResponse } from '@grpc/chat';
import { getEmotionScores } from './scorers/emotion-scorer';

import { ChatPromptTemplate } from '@langchain/core/prompts';
import { gemini } from '../langchain/gemini';

@Injectable()
export class ChatService {
    async handleChat(request: ChatRequest): Promise<ChatResponse> {
        console.log('[handleChat] called with:', request);
        const { userId, message } = request;

        // 1. 프롬프트 구성
        const prompt = ChatPromptTemplate.fromMessages([
            [
                'system',
                '너는 사용자에게 따뜻하고 진정성 있는 응답을 제공하는 감정 서포터야. 다음 사용자의 메시지에 자연스럽게 대답해줘.',
            ],
            ['human', '{message}'],
        ]);

        // 2. 모델과 연결
        const chain = prompt.pipe(gemini);

        // 3. Gemini에 메시지 전달 → 응답 받기
        const res = await chain.invoke({ message });
        const reply = res.text; // chain의 결과는 `.text`로 반환됨

        // 4. 감정 점수 분석
        const { dep, anx, str } = await getEmotionScores(message);

        return {
            reply,
            depScore: dep,
            anxScore: anx,
            strScore: str,
        };
    }
}
