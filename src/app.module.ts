// src/app.module.ts
import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { ChatModule } from './chat/chat.module';

@Module({
  imports: [
    ChatModule,  // ChatModule을 불러와서 사용
    ConfigModule.forRoot({
      isGlobal: true, // 모든 모듈에서 ConfigService를 사용할 수 있도록 전역 설정
    }),
  ],
})
export class AppModule {}
