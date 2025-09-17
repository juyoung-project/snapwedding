import { apiClientPost } from '@/lib/commonApi';
import { LoginDto, LoginDtoSchema } from '@/schemas/auth';
import { AxiosResponse } from 'axios';

type LoginSuccess = { message: string };

export async function login(payload: LoginDto): Promise<AxiosResponse<LoginSuccess>> {
  const validated = LoginDtoSchema.parse(payload);
  return apiClientPost<LoginSuccess>('/api/auth/login', validated);
}
