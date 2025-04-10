import { Controller, Get } from '@nestjs/common';
import { AppService } from './app.service';

@Controller('profile')  // 이렇게 설정해주면 /profile 경로에 매핑됩니다.
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  async getCounts() {
    console.log('Received GET request for /profile');
    return await this.appService.getCounts();
  }
}
