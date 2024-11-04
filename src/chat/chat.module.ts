// src/chat/chat.module.ts
import { Module } from '@nestjs/common';
import { ChatController } from './chat.controller';
import { ChatService } from './chat.service';
import { HttpModule } from '@nestjs/axios';
import { ConfigModule } from '@nestjs/config';

@Module({
  imports: [
    HttpModule,
    ConfigModule, 
  ],
  controllers: [ChatController],
  providers: [ChatService],
  exports: [ChatService], 
})
export class ChatModule {}
