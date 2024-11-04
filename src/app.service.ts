import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { lastValueFrom } from 'rxjs';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class AppService {
  constructor(
    private readonly httpService: HttpService,
    private readonly configService: ConfigService,
  ) {}

  async postChat(chatMessage: string): Promise<string> {
    const apiKey = this.configService.get<string>('OPENAI_API_KEY');
    const apiUrl = 'https://api.openai.com/v1/chat/completions';

    const payload = {
      model: 'gpt-3.5-turbo',  // 사용할 모델
      messages: [{ role: 'user', content: chatMessage }],
      max_tokens: 100,
      temperature: 0.7,
    };

    const headers = {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${apiKey}`,
    };

    try {
      const response = await lastValueFrom(
        this.httpService.post(apiUrl, payload, { headers }),
      );
      return response.data.choices[0].message.content.trim(); // GPT 응답의 첫 번째 메시지 내용 반환
    } catch (error) {
      console.error('Error communicating with OpenAI API:', error.response?.data || error.message);
      throw new Error('Failed to get a response from GPT API');
    }
  }
}
