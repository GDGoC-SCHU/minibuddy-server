import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { ChatModule } from './chat/chat.module';
import { DepModule } from './dep/dep.module';
import { MciModule } from './mci/mci.module';
import { Chat } from './chat/chat.entity';
import { Dep } from './dep/dep.entity';
import { Mci } from './mci/mci.entity';
import { AppController } from './app.controller';
import { AppService } from './app.service';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    TypeOrmModule.forRootAsync({
      imports: [ConfigModule],
      useFactory: (configService: ConfigService) => ({
        type: 'mysql',
        host: configService.get<string>('DB_HOST'),
        port: configService.get<number>('DB_PORT'),
        username: configService.get<string>('DB_USERNAME'),
        password: configService.get<string>('DB_PASSWORD'),
        database: configService.get<string>('DB_NAME'),
        entities: [Chat, Dep, Mci],
        synchronize: true, 
      }),
      inject: [ConfigService],
    }),
    TypeOrmModule.forFeature([Mci, Dep]), // Mci와 Dep를 forFeature로 등록
    ChatModule,
    DepModule,
    MciModule,
  ],
  providers: [AppService],
  controllers: [AppController],
  exports: [AppService],
})
export class AppModule {}
