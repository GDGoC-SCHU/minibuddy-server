import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ChatController } from './chat.controller';
import { ChatService } from './chat.service';
import { Chat } from './chat.entity';
import { HttpModule } from '@nestjs/axios';
import { ConfigModule } from '@nestjs/config';
import { DepModule } from '../dep/dep.module'; // DepModule 추가

@Module({
  imports: [
    TypeOrmModule.forFeature([Chat]), // Chat 엔티티 리포지토리 등록
    HttpModule,
    ConfigModule,
    DepModule, // DepModule 추가
  ],
  controllers: [ChatController],
  providers: [ChatService],
  exports: [ChatService],
})
export class ChatModule {}
