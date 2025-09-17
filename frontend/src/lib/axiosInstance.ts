import axios, { AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

let isRefreshing = false;
let refreshPromise: Promise<unknown> | null = null;

export const applyInterceptors = (inst: AxiosInstance) => {
  inst.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
      return config;
    },
    (error) => Promise.reject(error),
  );

  inst.interceptors.response.use(
    (response: AxiosResponse) => response?.data,
    async (error) => {
      const originalRequest = error.config as unknown as InternalAxiosRequestConfig;
      const status = error.response?.status as number | undefined;

      if (!status || status !== 401 || originalRequest?.url?.includes('/api/auth/refresh')) {
        return Promise.reject(error);
      }

      if (originalRequest?._retry) {
        if (typeof window !== 'undefined') {
          window.location.href = '/login';
        }
        return Promise.reject(error);
      }

      originalRequest._retry = true;

      try {
        if (!isRefreshing) {
          isRefreshing = true;
          refreshPromise = axios.post('/api/auth/refresh', null, {
            withCredentials: true,
            headers: { 'Content-Type': 'application/json' },
          });
        }
        await refreshPromise;
        return inst(originalRequest);
      } catch (e) {
        if (typeof window !== 'undefined') {
          window.location.href = '/login';
        }
        return Promise.reject(e);
      } finally {
        isRefreshing = false;
        refreshPromise = null;
      }
    },
  );
};

export const instance: AxiosInstance = axios.create({
  timeout: 10000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

applyInterceptors(instance);
