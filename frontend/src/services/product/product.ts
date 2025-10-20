import { apiClientDelete, apiClientGet, apiClientPut, apiClientPutUpload, apiClientUpload } from '@/lib/commonApi';
import { ProductDto, ProductOrderDto, ProductSaveDto, ProductSuccessDto } from '@/types/domain/product';
import { AxiosResponse } from 'axios';

export async function getProduct(): Promise<AxiosResponse<ProductDto[]>> {
  return await apiClientGet<ProductDto[]>('/api/product');
}

export async function updateProductOrder(payload: ProductOrderDto[]): Promise<AxiosResponse<ProductOrderDto[]>> {
  return await apiClientPut<ProductOrderDto[]>('/api/product?type=order', payload);
}

export async function getProductById(id: number | string | null): Promise<AxiosResponse<ProductDto>> {
  return await apiClientGet<ProductDto>(`/api/product/${id}`);
}

export async function updateProduct(id: string, payload: ProductSaveDto): Promise<AxiosResponse<ProductDto>> {
  const form = new FormData();
  console.log('id   ', id);
  console.log('payload   ', payload);
  payload['expertId'] = '1';
  payload['productType'] = 'SNAP';
  form.append('expertProduct', JSON.stringify(payload));

  if (payload.file) {
    form.append('file', payload.file);
  }
  return await apiClientPutUpload<ProductDto>(`/api/product/${id}`, form);
}

export async function deleteProduct(id: number | string | null): Promise<AxiosResponse<ProductDto>> {
  console.log(id);
  return await apiClientDelete<ProductDto>(`/api/product/${id}`);
}

export async function saveProduct(payload: ProductSaveDto): Promise<AxiosResponse<ProductSuccessDto>> {
  const form = new FormData();
  payload['expertId'] = '1';
  payload['productType'] = 'SNAP';
  form.append('expertProduct', JSON.stringify(payload));

  if (payload.file) {
    form.append('file', payload.file);
  }

  return await apiClientUpload<ProductSuccessDto>('/api/product', form);
}
