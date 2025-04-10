import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DepService } from './dep.service';
import { Dep } from './dep.entity';
import { HttpModule } from '@nestjs/axios'; // HttpModule import 추가

@Module({
  imports: [
    TypeOrmModule.forFeature([Dep]),
    HttpModule, // HttpModule 추가
  ],
  providers: [DepService],
  exports: [DepService],
})
export class DepModule {}