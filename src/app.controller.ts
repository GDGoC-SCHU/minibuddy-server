import { Controller, Post, Body } from '@nestjs/common';
import { AppService } from './app.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Post('/chat')
  async handleChat(@Body() body: { chat: string }) {
    const { chat } = body;
    const response = await this.appService.postChat(chat);
    return { response };
  }
}
