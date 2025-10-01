import { apiClientGet, apiClientPut, apiClientUpload } from '@/lib/commonApi';
import { ProductDto, ProductOrderDto, ProductSaveDto, ProductSuccessDto } from '@/types/domain/product';
import { AxiosResponse } from 'axios';

export async function getProduct(): Promise<AxiosResponse<ProductDto[]>> {
  return await apiClientGet<ProductDto[]>('/api/product');
}

export async function updateProductOrder(payload: ProductOrderDto[]): Promise<AxiosResponse<ProductOrderDto[]>> {
  return await apiClientPut<ProductOrderDto[]>('/api/product?type=order', payload);
}

export async function saveProduct(payload: ProductSaveDto): Promise<AxiosResponse<ProductSuccessDto>> {
  const form = new FormData();
  payload['expertId'] = '1';
  payload['productType'] = 'SNAP';
  /*form.append('productNm', payload.productNm);
  form.append('price', String(payload.price));
  form.append('order', String(payload.order));
  form.append('durationHours', String(payload.durationHours));
  form.append('description', payload.description ?? '');
  form.append('productType', payload.productType);
  form.append('postingYn', String(payload.postingYn));
  form.append('useYn', String(payload.useYn));
  form.append('badge', payload.badge ?? '');*/
  form.append('expertProduct', JSON.stringify(payload));

  if (payload.file) {
    form.append('file', payload.file);
  }

  return await apiClientUpload<ProductSuccessDto>('/api/product', form);
}
