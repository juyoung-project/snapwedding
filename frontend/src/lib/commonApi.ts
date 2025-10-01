import { instance } from './axiosInstance';
// ------------------------------
// 기본 API (BASE_API_URL 기반)
// ------------------------------
import { AxiosResponse } from 'axios';

export const apiGet = async <T = unknown>(
  url: string,
  params: Record<string, unknown> = {},
): Promise<AxiosResponse<T>> => {
  url = `${process.env.NEXT_PUBLIC_API_URL}${url}`;
  return instance.get<T>(url, { params });
};

export const apiPost = async <T = unknown>(url: string, data: unknown = {}): Promise<AxiosResponse<T>> => {
  url = `${process.env.NEXT_PUBLIC_API_URL}${url}`;
  return instance.post<T>(url, data);
};

export const apiPut = async <T = unknown>(url: string, data: unknown = {}): Promise<AxiosResponse<T>> => {
  url = `${process.env.NEXT_PUBLIC_API_URL}${url}`;
  return instance.put<T>(url, data);
};

export const apiDelete = async <T = unknown>(url: string, data: unknown = {}): Promise<AxiosResponse<T>> => {
  url = `${process.env.NEXT_PUBLIC_API_URL}${url}`;
  return instance.delete<T>(url, { data });
};

export const apiUpload = async <T = unknown>(url: string, formData: FormData): Promise<AxiosResponse<T>> => {
  url = `${process.env.NEXT_PUBLIC_API_URL}${url}`;
  return instance.post<T>(url, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

export const apiClientGet = async <T = unknown>(
  url: string,
  params: Record<string, unknown> = {},
): Promise<AxiosResponse<T>> => {
  return instance.get<T>(url, { params });
};

export const apiClientPost = async <T = unknown>(url: string, data: unknown = {}): Promise<AxiosResponse<T>> => {
  return instance.post<T>(url, data);
};

export const apiClientPut = async <T = unknown>(url: string, data: unknown = {}): Promise<AxiosResponse<T>> => {
  return instance.put<T>(url, data);
};

export const apiClientDelete = async <T = unknown>(url: string, data: unknown = {}): Promise<AxiosResponse<T>> => {
  return instance.delete<T>(url, { data });
};

export const apiClientUpload = async <T = unknown>(url: string, formData: FormData): Promise<AxiosResponse<T>> => {
  return instance.post<T>(url, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

export const apiDownload = async (
  url: string,
  params: Record<string, unknown> = {},
  downloadFileName?: string,
): Promise<void> => {
  const response = await instance.get<Blob>(url, {
    params,
    responseType: 'blob',
  });
  handleDownload(response, downloadFileName);
};

// ------------------------------
// 공통 다운로드 유틸
// ------------------------------
const handleDownload = (response: AxiosResponse<Blob>, downloadFileName?: string) => {
  const blob = new Blob([response.data], { type: response.headers['content-type'] });
  const urlBlob = window.URL.createObjectURL(blob);

  const link = document.createElement('a');
  link.href = urlBlob;

  const disposition = response.headers['content-disposition'];
  const fileName = downloadFileName || getFileNameFromDisposition(disposition) || 'downloaded_file';

  link.download = decodeURIComponent(fileName);
  document.body.appendChild(link);
  link.click();
  link.remove();

  window.URL.revokeObjectURL(urlBlob);
};

const getFileNameFromDisposition = (disposition?: string): string | null => {
  if (!disposition) return null;
  const match = disposition.match(/filename\*=UTF-8''(.+?)(;|$)/);
  if (match) return match[1];
  const fallback = disposition.match(/filename="(.+?)"/);
  return fallback ? fallback[1] : null;
};
