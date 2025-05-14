import { Module } from '@nestjs/common';
import { ChatGrpcService } from './grpc/chat.grpc-service';
import { ChatService } from './chat/chat.service';

@Module({
  controllers: [ChatGrpcService],
  providers: [ChatService],
})
export class AppModule { }
