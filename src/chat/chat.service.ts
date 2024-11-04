import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Chat } from './chat.entity';
import { ConfigService } from '@nestjs/config';
import { lastValueFrom } from 'rxjs';

@Injectable()
export class ChatService {
  constructor(
    private readonly httpService: HttpService,
    private readonly configService: ConfigService,
    @InjectRepository(Chat)
    private readonly chatRepository: Repository<Chat>,
  ) {}

  async getChatResponse(userInput: string): Promise<string> {
    const apiKey = this.configService.get<string>('OPENAI_API_KEY');
    const apiUrl = 'https://api.openai.com/v1/chat/completions';

    // 사용자 입력에 대한 GPT 프롬프트 생성
    const prompt = `너는 혼자 사는 사람들과 대화하는 친구야.
    가장 중요하게 지켜야할 것이 있어. 응답 메시지를 tts로 출력했을 때 읽어서 이상하지 않은 것만 반환해. 
    이것도 정말 중요해 꼭 지켜야 하는 규칙이야. 대답에 'imoticons'을 포함하지마. 또한 응답 json에 글씨로 만든 imoticons도 사용하지 말것. 😊과 같은 이모티콘 사용하지마.
    단순히 text만 사용해서 '이모티콘이 없는 문장 형식'으로 대답할 것.
    :)과 같은 웃는 얼굴 표시 사용하지마. 느낌표와 물음표도 사용하지 말것.
    사용자가 대화를 시작하면 존댓말로 대답할 것.
    혼자산다는 말도 포함하지마. 도와준다는 말은 포함하지마.
    너무 긴 대화는 줄여서 대답해주고 이어서 대화할 수 있도록 대답해줘.: ${userInput}`;

    const payload = {
      model: 'gpt-3.5-turbo',
      messages: [{ role: 'user', content: prompt }],
      max_tokens: 100,
      temperature: 0.7,
    };

    const headers = {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${apiKey}`,
    };

    try {
      // GPT API에 요청
      const response = await lastValueFrom(
        this.httpService.post(apiUrl, payload, { headers }),
      );
      const gptResponse = response.data.choices[0].message.content.trim();

      // 사용자 대화를 DB에 저장
      const chat = new Chat();
      chat.message = userInput;
      await this.chatRepository.save(chat);

      return gptResponse;
    } catch (error) {
      console.error('Error communicating with OpenAI API:', error.response?.data || error.message);
      throw new Error('Failed to get a response from GPT API');
    }
  }
}
