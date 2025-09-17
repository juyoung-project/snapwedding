'use client';

import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

export default function Bridge() {
  const router = useRouter();
  useEffect(() => {
    fetch('http://localhost:8090/api/oauth/token', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        if (data.accessToken) {
          localStorage.setItem('accessToken', data.accessToken);
          localStorage.setItem('refreshToken', data.refreshToken);
          router.replace('/dashboard');
        } else {
          router.replace('/login');
        }
      })
      .catch((error) => {
        console.error('Fetch error:', error);
        router.replace('/login');
      });
  }, []);

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <h1 className="text-2xl font-bold">Bridge</h1>
    </div>
  );
}
