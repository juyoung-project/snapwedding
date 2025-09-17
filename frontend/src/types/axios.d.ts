import 'axios';

declare module 'axios' {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  interface InternalAxiosRequestConfig<D = unknown> {
    _retry?: boolean;
  }
}
