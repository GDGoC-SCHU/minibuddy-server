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

  async postChat(userInput: string): Promise<string> {
    const apiKey = this.configService.get<string>('OPENAI_API_KEY');
    const apiUrl = 'https://api.openai.com/v1/chat/completions';

    // 역할을 설명하는 프롬프트와 함께 사용자 입력을 추가하여 GPT에 전달
    const prompt = `너는 혼자 사는 사람들을 대상으로 하는 친구의 역할이야. 사용자가 대화를 시작하면 친화적으로 대답해줘. 단 여기서 이모티콘은 포함하지 말고 너무 긴 대화는 줄여서 대답해줘 : ${userInput}`;

    const payload = {
      model: 'gpt-3.5-turbo',  // 사용할 모델
      messages: [{ role: 'user', content: prompt }],
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
