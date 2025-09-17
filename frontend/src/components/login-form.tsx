'use client';

import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import userImage from '@/assets/images/user.svg';
import Image from 'next/image';
import React from 'react';
import { LoginDto } from '@/types/login';
import { useMutation } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import { login } from '@/services/auth/client';

export function LoginForm({ className, ...props }: React.ComponentProps<'div'>) {
  const router = useRouter();
  const [data, setData] = React.useState<LoginDto>({
    email: '',
    password: '',
  });

  const { mutateAsync } = useMutation({
    mutationFn: (payload: LoginDto) => login(payload),
    retry: 0, // 로그인은 재시도 비권장
    onSuccess: () => {
      router.replace('/dashboard');
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const naverUrl = process.env.NEXT_PUBLIC_NAVER_OAUTH_URL;
  const handleKakaoLogin = () => {};

  const handleLogin = async () => {
    if (!data) return;

    await mutateAsync(data);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  return (
    <div className={cn('flex flex-col gap-6', className)} {...props}>
      <Card className="overflow-hidden p-0">
        <CardContent className="grid p-0 md:grid-cols-2">
          <form className="p-6 md:p-8">
            <div className="flex flex-col gap-6">
              <div className="flex flex-col items-center text-center">
                <h1 className="text-2xl font-bold">WDS 로그인</h1>
                <p className="text-muted-foreground text-balance">Login to your Acme Inc account</p>
              </div>
              <div className="grid gap-3">
                <Label htmlFor="email">이메일</Label>
                <Input
                  id="email"
                  name="email"
                  type="email"
                  placeholder="m@example.com"
                  required
                  onChange={handleInputChange}
                />
              </div>
              <div className="grid gap-3">
                <div className="flex items-center">
                  <Label htmlFor="password">비밀번호</Label>
                </div>
                <Input id="password" name="password" type="password" required onChange={handleInputChange} />
              </div>
              <Button type="button" className="w-full" onClick={handleLogin}>
                Login
              </Button>
              <div className="after:border-border relative text-center text-sm after:absolute after:inset-0 after:top-1/2 after:z-0 after:flex after:items-center after:border-t">
                <span className="bg-card text-muted-foreground relative z-10 px-2">Or continue with</span>
              </div>
              <div className="grid grid-cols-2 gap-4">
                {/* Naver */}
                <Button
                  variant="outline"
                  type="button"
                  className="w-full"
                  onClick={() => (window.location.href = naverUrl as string)}
                >
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="20" height="20" aria-hidden="true">
                    <rect x="2" y="2" width="20" height="20" rx="4" fill="#03C75A" />
                    <path d="M7 7h3l3 4.5V7h4v10h-3l-3-4.5V17H7V7z" fill="#ffffff" />
                  </svg>
                  <span className="sr-only">Login with Naver</span>
                </Button>

                {/* Kakao */}
                <Button variant="outline" type="button" className="w-full" onClick={handleKakaoLogin}>
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="20" height="20" aria-hidden="true">
                    <rect x="2" y="2" width="20" height="20" rx="4" fill="#FEE500" />
                    <path d="M8 7h2v4l3-4h2l-2.8 3.7L15 17h-2l-2-3-1 1.3V17H8V7z" fill="#3C1E1E" />
                  </svg>
                  <span className="sr-only">Login with Kakao</span>
                </Button>
              </div>

              <div className="text-center text-sm">
                Don&apos;t have an account?{' '}
                <a href="#" className="underline underline-offset-4">
                  Sign up
                </a>
              </div>
            </div>
          </form>
          <div className="bg-muted relative hidden md:block">
            <Image
              src={userImage}
              alt="Image"
              className="absolute inset-0 h-full w-full object-cover dark:brightness-[0.2] dark:grayscale"
            />
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
