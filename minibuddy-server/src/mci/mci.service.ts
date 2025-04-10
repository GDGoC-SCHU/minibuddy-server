import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Mci } from './mci.entity';

@Injectable()
export class MciService {
  constructor(
    @InjectRepository(Mci)
    private readonly mciRepository: Repository<Mci>,
  ) {}

  // 특정 user_id에 대한 MCI count 조회
  async getMciCountByUserId(userId: number): Promise<number> {
    const mciRecord = await this.mciRepository.findOne({ where: { user_id: userId } });
    return mciRecord ? mciRecord.count : 0;
  }

  // 전체 MCI count 조회 (모든 사용자 합산)
  async getTotalMciCount(): Promise<number> {
    const mciRecords = await this.mciRepository.find();
    return mciRecords.reduce((sum, record) => sum + record.count, 0);
  }
}
