'use client';

import Image from 'next/image';
import { useEffect, useRef, useState } from 'react';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import { ProductGridSortable } from '@/components/product-cards';
import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Textarea } from '@/components/ui/textarea';
import {
  deleteProduct,
  getProduct,
  getProductById,
  saveProduct,
  updateProduct,
  updateProductOrder,
} from '@/services/product/product';
import { ProductDto, ProductOrderDto, ProductSaveDto } from '@/types/domain/product';

// 생성/수정 폼 컴포넌트
function ProductForm(props: {
  product?: ProductDto | null;
  onSubmit: (data: ProductSaveDto) => void;
  onCancel: () => void;
  onDelete?: () => void;
}) {
  const { product, onSubmit, onCancel, onDelete } = props;
  const isEditMode = !!product;

  const fileInputRef = useRef<HTMLInputElement | null>(null);
  const [selectedFileName, setSelectedFileName] = useState<string>('');

  useEffect(() => {
    setSelectedFileName('');
  }, [product]);

  return (
    <form
      className="mt-2 space-y-4"
      onSubmit={(e) => {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const data = Object.fromEntries(formData.entries()) as unknown as ProductSaveDto;
        onSubmit(data);
      }}
    >
      <div className="space-y-2">
        <Label htmlFor="productNm">제품명</Label>
        <Input
          id="productNm"
          name="productNm"
          defaultValue={product?.productNm}
          placeholder="예: 스냅 프리미엄"
          required
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="price">가격</Label>
        <Input
          id="price"
          name="price"
          type="number"
          min={0}
          defaultValue={product?.price}
          placeholder="예: 12900"
          required
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="durationHours">진행시간</Label>
        <Input
          id="durationHours"
          name="durationHours"
          type="number"
          min={0}
          defaultValue={product?.durationHours}
          placeholder="예: 5시간"
          required
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="description">제품설명</Label>
        <Textarea
          placeholder="예: 해당 제품은 작가 2명 보조 2명이 참여합니다."
          name="description"
          id="description"
          defaultValue={product?.description}
        />
      </div>

      <div className="space-y-2">
        <Label>이미지</Label>
        {isEditMode && product?.thumbnailFileId && !selectedFileName && (
          <div className="relative aspect-video w-full overflow-hidden rounded-md">
            <Image
              src={`http://localhost:8090/api/file/file-download/${product.thumbnailFileId}`}
              alt="Current thumbnail"
              fill
              className="object-cover"
            />
          </div>
        )}
        <Input
          ref={fileInputRef}
          id="file"
          name="file"
          type="file"
          accept="image/*"
          className="hidden"
          onChange={(e) => {
            const f = e.target.files?.[0];
            setSelectedFileName(f?.name || '');
          }}
        />
        <div className="flex items-center gap-2">
          <Button type="button" onClick={() => fileInputRef.current?.click()}>
            {isEditMode ? '이미지 변경' : '이미지 업로드'}
          </Button>
          {selectedFileName && <span className="text-sm text-muted-foreground">{selectedFileName}</span>}
        </div>
      </div>

      <div className="space-y-2">
        <Label>게시여부</Label>
        <RadioGroup defaultValue={product?.postingYn || 'Y'} className="flex gap-3" name="postingYn">
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="Y" id="post-y" />
            <Label htmlFor="post-y">게시</Label>
          </div>
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="N" id="post-n" />
            <Label htmlFor="post-n">미게시</Label>
          </div>
        </RadioGroup>
      </div>

      <div className="space-y-2">
        <Label>사용여부</Label>
        <RadioGroup defaultValue={product?.useYn || 'Y'} className="flex gap-3" name="useYn">
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="Y" id="use-y" />
            <Label htmlFor="use-y">사용</Label>
          </div>
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="N" id="use-n" />
            <Label htmlFor="use-n">미사용</Label>
          </div>
        </RadioGroup>
      </div>

      <div className="space-y-2">
        <Label>배지</Label>
        <Select name="badge" defaultValue={product?.badge}>
          <SelectTrigger>
            <SelectValue placeholder="선택(옵션)" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="new">신규</SelectItem>
            <SelectItem value="sale">세일</SelectItem>
            <SelectItem value="recommend">추천</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div className="flex justify-end gap-2 pt-2">
        <Button type="button" variant="outline" onClick={onCancel}>
          취소
        </Button>
        {isEditMode && (
          <Button type="button" className={'bg-amber-600 hover:bg-gray-400'} onClick={onDelete}>
            삭제
          </Button>
        )}
        <Button type="submit">{isEditMode ? '저장' : '추가'}</Button>
      </div>
    </form>
  );
}

export default function ProductsPage() {
  const [products, setProducts] = useState<ProductDto[]>([]);
  const queryClient = useQueryClient();

  const [isFormOpen, setIsFormOpen] = useState(false);
  const [editingProductId, setEditingProductId] = useState<number | string | null>(null);

  const { data, isLoading } = useQuery<ProductDto[]>({
    queryKey: ['product'],
    queryFn: async () => {
      const res = await getProduct();
      return res.data;
    },
    retry: 0,
  });

  const { data: editingProduct, isLoading: isEditingProductLoading } = useQuery({
    queryKey: ['product', editingProductId],
    queryFn: async () => {
      if (!editingProductId) return null;
      const res = await getProductById(editingProductId);
      return res.data;
    },
    enabled: !!editingProductId,
  });

  useEffect(() => {
    if (data) setProducts(data);
  }, [data]);

  const addMutation = useMutation({
    mutationFn: (payload: ProductSaveDto) => saveProduct(payload),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['product'] }),
    onError: (err: unknown) => console.error(err),
  });

  const updateMutation = useMutation({
    // mutationFn이 id와 payload를 포함하는 객체를 받도록 수정
    mutationFn: ({ id, payload }: { id: string; payload: ProductSaveDto }) => updateProduct(id, payload), // id를 직접 사용 (String(id) 안 해도 되는 경우)

    // 성공 시 쿼리 캐시를 무효화하여 최신 데이터를 불러오도록 처리
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['product'] }); // 'product' 쿼리 키로 데이터를 새로고침
    },

    // 오류 처리 (optional)
    onError: (err: unknown) => {
      console.error('Error updating product:', err);
    },
  });

  const deleteMutation = useMutation({
    mutationFn: () => deleteProduct(editingProductId!),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['product'] }),
    onError: (err: unknown) => console.error(err),
  });

  const reOrderMutation = useMutation({
    mutationFn: (payload: ProductOrderDto[]) => updateProductOrder(payload),
    onMutate: async () => {
      await queryClient.cancelQueries({ queryKey: ['product'] });
      const prev = queryClient.getQueryData<ProductDto[]>(['product']) ?? [];
      return { prev };
    },
    onError: (err, _vars, context) => {
      if (context?.prev) setProducts(context.prev);
      console.error(err);
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ['product'] }),
  });

  const onReorder = (next: ProductDto[], orderList: ProductOrderDto[]) => {
    setProducts(next);
    reOrderMutation.mutate(orderList);
  };

  const handleFormSubmit = (data: ProductSaveDto) => {
    if (editingProductId) {
      updateMutation.mutate({ id: String(editingProductId!), payload: data });
    } else {
      addMutation.mutate(data);
    }
    closeForm();
  };

  const openFormForEdit = (id: number | string) => {
    setEditingProductId(id);
    setIsFormOpen(true);
  };

  const openFormForAdd = () => {
    setEditingProductId(null);
    setIsFormOpen(true);
  };

  const closeForm = () => {
    setIsFormOpen(false);
    setEditingProductId(null);
  };

  const handleDeleteProduct = () => {
    alert(1);
    deleteMutation.mutate();
  };

  if (isLoading) return <div className="px-4 py-6">로딩 중...</div>;

  return (
    <div className="px-4 py-6">
      <h1 className="mb-4 text-2xl font-semibold">제품 관리</h1>
      <ProductGridSortable
        products={products}
        onReorder={onReorder}
        onOpen={openFormForEdit}
        onAddClick={openFormForAdd}
      />

      <Dialog open={isFormOpen} onOpenChange={(open) => !open && closeForm()}>
        <DialogContent className="w-full sm:max-w-md md:max-w-xl lg:max-w-2xl">
          <DialogHeader>
            <DialogTitle>{editingProductId ? '제품 수정' : '새 제품 추가'}</DialogTitle>
          </DialogHeader>
          <ProductForm
            product={editingProduct}
            onSubmit={handleFormSubmit}
            onCancel={closeForm}
            onDelete={handleDeleteProduct}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}
