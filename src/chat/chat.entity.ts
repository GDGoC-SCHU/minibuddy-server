import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity('chat')
export class Chat {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ type: 'text' })
  message: string; // 사용자 대화를 저장할 필드

  @Column({ type: 'int', nullable: false, default: 1 })
  user_id: number; // 임시로 user_id를 1로 설정하여 사용
}
