import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Mci } from './mci/mci.entity';
import { Dep } from './dep/dep.entity';

@Injectable()
export class AppService {
  constructor(
    @InjectRepository(Mci)
    private readonly mciRepository: Repository<Mci>,
    
    @InjectRepository(Dep)
    private readonly depRepository: Repository<Dep>,
  ) {}

  // mci와 dep 테이블의 count 조회
  async getCounts(): Promise<{ depression: number, mci: number }> {
    const depEntry = await this.depRepository.findOne({ where: { user_id: 1 } });
    const mciEntry = await this.mciRepository.findOne({ where: { user_id: 1 } });

    return {
      depression: depEntry ? depEntry.count : 0,
      mci: mciEntry ? mciEntry.count : 0,
    };
  }
}
