import { TypeYn } from '@/types/base';

export interface ProductDto {
  id: number | string;
  productNm: string;
  price: number;
  order: number;
  durationHours: number;
  description: string;
  badge: string;
  file: File;
  thumbnailFileId: number | string;
  postingYn: TypeYn;
  useYn: TypeYn;
}

export interface ProductSuccessDto extends ProductDto {
  message: string;
}

export interface ProductSaveDto {
  expertId: number | string;
  productNm: string;
  price: number;
  order: number;
  durationHours: number;
  description: string;
  file: File;
  productType: string;
  postingYn: TypeYn;
  useYn: TypeYn;
  badge: string;
}

export interface ProductOrderDto {
  id: number | string;
  order: number;
}
