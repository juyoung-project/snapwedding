// app/api/auth/login/route.ts
import { NextResponse } from 'next/server';
import { LoginDtoSchema, LoginResponseSchema, type LoginDto, type LoginResponse } from '@/schemas/auth';
import { apiPost } from '@/lib/commonApi'; // 실제 API 요청을 보내는 함수

export async function POST(req: Request) {
  // 클라이언트에서 전송된 데이터 받기
  const payload: LoginDto = await req.json();
  // 요청 데이터 유효성 검증
  const validated = LoginDtoSchema.parse(payload); // 데이터 검증

  const data = await apiPost<LoginDto>('/api/auth/login', validated);
  const response: LoginResponse = LoginResponseSchema.parse(data);

  const res = NextResponse.json({ message: '로그인 성공' }, { status: 200 });

  const isProd = process.env.NODE_ENV === 'production';

  // access_token 저장 (짧은 수명 권장: 예시 15분)
  res.cookies.set('access_token', response.token.accessToken, {
    httpOnly: true,
    secure: isProd,
    sameSite: 'lax',
    path: '/',
    maxAge: 60 * 15,
  });

  // refresh_token 저장 (긴 수명 권장: 예시 7일)
  res.cookies.set('refresh_token', response.token.refreshToken, {
    httpOnly: true,
    secure: isProd,
    sameSite: 'lax',
    path: '/',
    maxAge: 60 * 60 * 24 * 7,
  });

  return res;
}
