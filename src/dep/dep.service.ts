import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { lastValueFrom } from 'rxjs';
import { ConfigService } from '@nestjs/config';
import { Dep } from './dep.entity';

@Injectable()
export class DepService {
  constructor(
    private readonly httpService: HttpService,
    private readonly configService: ConfigService,
    @InjectRepository(Dep)
    private readonly depRepository: Repository<Dep>,
  ) {}

  async analyzeDepression(userInput: string): Promise<void> {
    const apiKey = this.configService.get<string>('OPENAI_API_KEY');
    const apiUrl = 'https://api.openai.com/v1/chat/completions';

    const prompt = `사용자가 다음과 같은 대화를 보냈습니다: "${userInput}". 이 대화에서 우울증 증상이 나타나는지 분석하고, 나타난다면 '증상 있음'이라고 응답해 주세요.`;

    const payload = {
      model: 'gpt-3.5-turbo',
      messages: [{ role: 'user', content: prompt }],
      max_tokens: 100,
      temperature: 0.5,
    };

    const headers = {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${apiKey}`,
    };

    try {
      const response = await lastValueFrom(
        this.httpService.post(apiUrl, payload, { headers }),
      );
      const gptResponse = response.data.choices[0].message.content.trim();

      // GPT 응답에 '증상 있음'이 포함된 경우 count 증가
      if (gptResponse.includes('증상 있음')) {
        await this.incrementDepressionCount();
      }
    } catch (error) {
      console.error('Error analyzing depression symptoms:', error.response?.data || error.message);
    }
  }


  // dep 테이블의 count 값 증가 여기 userid와 매칭해서 수정해야
  private async incrementDepressionCount(): Promise<void> {
    // 첫 번째 Dep 레코드를 가져오거나 없다면 새로 생성
    let depEntry = await this.depRepository.findOneBy({}); // 첫 번째 레코드 조회

    if (!depEntry) {
      // Dep 엔트리가 없을 때 새로 생성
      depEntry = this.depRepository.create({ count: 1 });
    } else {
      // 기존 엔트리가 있다면 count 증가
      depEntry.count += 1;
    }

    await this.depRepository.save(depEntry);
  }
}
