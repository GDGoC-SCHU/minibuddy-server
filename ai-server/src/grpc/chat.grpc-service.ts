import { Controller, Injectable } from '@nestjs/common';
import { GrpcMethod } from '@nestjs/microservices';
import { ChatRequest, ChatResponse } from './chat';
import { ChatService } from '../chat/chat.service';
//import { MemoryQuestionService } from '../memory/memory-question.service';
//import { MemoryAnswerService } from '../memory/memory-answer.service';

@Controller()
@Injectable()
export class ChatGrpcService {
    constructor(
        private readonly chatService: ChatService,
        // private readonly memoryQuestionService: MemoryQuestionService,
        // private readonly memoryAnswerService: MemoryAnswerService,
    ) { }

    @GrpcMethod('ChatAiService', 'GetChatResponse')
    async getChatResponse(request: ChatRequest): Promise<ChatResponse> {
        return this.chatService.handleChat(request);

    }
}
