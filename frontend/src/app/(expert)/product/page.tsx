'use client';

import { useEffect, useState } from 'react';
import { ProductGridSortable } from '@/components/product-cards';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { ProductDto, ProductOrderDto, ProductSaveDto } from '@/types/domain/product';
import { getProduct, saveProduct, updateProductOrder } from '@/services/product/product';

export default function ProductsPage() {
  // 화면 정렬/드래그 반영을 위한 로컬 상태
  const [products, setProducts] = useState<ProductDto[]>([]);
  const queryClient = useQueryClient();

  // getProduct가 AxiosResponse<ProductDto[]>를 반환한다고 가정하고 data를 배열로 변환
  const { data, isLoading } = useQuery<ProductDto[]>({
    queryKey: ['product'],
    queryFn: async () => {
      const res = await getProduct(); // AxiosResponse<ProductDto[]>
      return res.data;
    },
    retry: 0,
  });

  useEffect(() => {
    console.log('data', data);
    if (data) setProducts(data);
  }, [data]);

  const { mutateAsync } = useMutation({
    mutationFn: (payload: ProductSaveDto) => saveProduct(payload),
    onSuccess: async () => {
      // 생성 후 목록 갱신
      await queryClient.invalidateQueries({ queryKey: ['product'] });
    },
    onError: (err: unknown) => {
      console.error(err);
    },
  });

  const reOrderMutation = useMutation({
    mutationFn: (payload: ProductOrderDto[]) => updateProductOrder(payload),
    onMutate: async () => {
      await queryClient.cancelQueries({ queryKey: ['product'] });
      const prev = queryClient.getQueryData<ProductDto[]>(['product']) ?? [];
      return { prev };
    },

    onError: (err, _vars, context) => {
      // 롤백
      if (context?.prev) setProducts(context.prev);
      console.error(err);
    },
    onSettled: async () => {
      await queryClient.invalidateQueries({ queryKey: ['product'] });
    },
  });

  const addProduct = async (payload: ProductSaveDto) => {
    await mutateAsync(payload);
  };

  const onReorder = (next: ProductDto[], orderList: ProductOrderDto[]) => {
    setProducts(next);
    reOrderMutation.mutate(orderList);
  };

  if (isLoading) return <div className="px-4 py-6">로딩 중...</div>;

  return (
    <div className="px-4 py-6">
      <h1 className="mb-4 text-2xl font-semibold">제품 관리</h1>
      <ProductGridSortable
        products={products} // 반드시 배열 전달
        onReorder={onReorder}
        onOpen={(id) => console.log('open', id)}
        onAdd={addProduct}
      />
    </div>
  );
}
