// src/schemas/auth.ts
import { z } from 'zod';

export const LoginDtoSchema = z.object({
  email: z.string().email('이메일 형식이 올바르지 않습니다.'),
  password: z.string().min(3, '비밀번호는 8자 이상이어야 합니다.'),
});
export type LoginDto = z.infer<typeof LoginDtoSchema>;

export const LoginResponseSchema = z.object({
  token: z.object({
    accessToken: z.string(),
    refreshToken: z.string(),
  }),
});
export type LoginResponse = z.infer<typeof LoginResponseSchema>;
