// main.ts
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { MicroserviceOptions, Transport } from '@nestjs/microservices';
import { join } from 'path';

async function bootstrap() {
    const app = await NestFactory.createMicroservice<MicroserviceOptions>(AppModule, {
        transport: Transport.GRPC,
        options: {
            package: 'chat',
            protoPath: join(__dirname, './grpc/chat.proto'),
            url: '0.0.0.0:8080', // Cloud Run의 요구사항: 반드시 PORT에 맞춰야 함
        },
    });

    await app.listen();
}
bootstrap();
