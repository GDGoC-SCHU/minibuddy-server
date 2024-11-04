import { Controller, Post, Body } from '@nestjs/common';
import { ChatService } from './chat.service';

@Controller('chat')
export class ChatController {
  constructor(private readonly chatService: ChatService) {}

  @Post()
  async handleChat(@Body() body: { chat: string }) {
    const { chat } = body;
    const response = await this.chatService.getChatResponse(chat);
    return { response };
  }
}
