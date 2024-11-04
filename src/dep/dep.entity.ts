import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity('dep')
export class Dep {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ type: 'int', default: 0 })
  count: number; // 우울증 증상 감지 횟수를 저장할 필드

  @Column({ type: 'int', default: 0})
  user_id: number; // 사용자 ID를 저장할 필드
}
