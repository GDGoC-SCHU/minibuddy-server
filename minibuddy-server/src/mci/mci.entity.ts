import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity('mci')
export class Mci {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ type: 'int', default: 0 })
  count: number; 

  @Column({ type: 'int', nullable: false, default: 1 })
  user_id: number; // 사용자 ID를 저장할 필드
}
