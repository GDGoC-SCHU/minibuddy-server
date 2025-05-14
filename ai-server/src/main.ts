// main.ts
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { MicroserviceOptions, Transport } from '@nestjs/microservices';
import { join } from 'path';

async function bootstrap() {
  const grpcApp = await NestFactory.createMicroservice<MicroserviceOptions>(AppModule, {
    transport: Transport.GRPC,
    options: {
      package: 'chat',
      protoPath: join(__dirname, './grpc/chat.proto'),
      url: '0.0.0.0:50051', // gRPC는 다른 포트
    },
  });
  grpcApp.listen();

  const httpApp = await NestFactory.create(AppModule);
  await httpApp.listen(process.env.PORT || 8081); // Cloud Run용
}
bootstrap();
