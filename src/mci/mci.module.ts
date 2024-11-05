import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Mci } from './mci.entity';
import { MciService } from './mci.service';

@Module({
  imports: [TypeOrmModule.forFeature([Mci])],
  providers: [MciService],
  exports: [MciService], // 다른 모듈에서 사용할 수 있도록 MciService를 export
})
export class MciModule {}
