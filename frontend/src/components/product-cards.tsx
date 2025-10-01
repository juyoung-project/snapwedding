'use client';

import Image from 'next/image';
import { useMemo, useRef, useState } from 'react';
import { closestCenter, DndContext, DragEndEvent, PointerSensor, useSensor, useSensors } from '@dnd-kit/core';
import { arrayMove, rectSortingStrategy, SortableContext, useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import { IconPlus } from '@tabler/icons-react';

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Textarea } from '@/components/ui/textarea';
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group';
import { ProductDto, ProductOrderDto, ProductSaveDto } from '@/types/domain/product';

type ProductGridSortableProps = {
  products: ProductDto[];
  onReorder: (next: ProductDto[], orderList: ProductOrderDto[]) => void;
  onOpen?: (id: string | number) => void;
  onAdd: (data: ProductSaveDto) => void;
};

export function ProductGridSortable({ products, onReorder, onOpen, onAdd }: ProductGridSortableProps) {
  const [open, setOpen] = useState(false);

  const sensors = useSensors(useSensor(PointerSensor, { activationConstraint: { distance: 6 } }));

  const ids = useMemo(() => products.map((p) => p.id), [products]);

  const handleDragEnd = (event: DragEndEvent) => {
    const { active, over } = event;
    if (!over || active.id === over.id) return;

    const oldIndex = ids.indexOf(active.id);
    const newIndex = ids.indexOf(over.id);
    if (oldIndex === -1 || newIndex === -1) return;
    const next = arrayMove(products, oldIndex, newIndex);

    const findActive = products.find((p) => p.id === active.id);
    const findOver = products.find((p) => p.id === over.id);

    let orderList = [] as ProductOrderDto[];
    if (findActive && findOver) {
      const swappedOrders = products.map((p) => {
        if (p.id === findActive.id) {
          return { id: p.id, order: newIndex };
        }
        if (p.id === findOver.id) {
          return { id: p.id, order: oldIndex };
        }
      });

      orderList = swappedOrders.filter(Boolean) as ProductOrderDto[];
    }
    onReorder(next, orderList);
  };

  return (
    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-6">
      <DndContext sensors={sensors} collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
        <SortableContext items={ids} strategy={rectSortingStrategy}>
          {products.map((p) => (
            <SortableProductCard key={p.id} product={p} onOpen={onOpen} />
          ))}
        </SortableContext>
      </DndContext>

      {/* 항상 마지막에 오는 추가(+) 카드: 드래그 불가 */}
      <Dialog open={open} onOpenChange={setOpen}>
        <DialogTrigger asChild>
          <Card
            role="button"
            aria-label="제품 추가"
            className="flex min-h-[220px] items-center justify-center rounded-xl border-2 border-dashed border-muted-foreground/30 bg-muted/20 p-4 text-muted-foreground transition-colors hover:border-muted-foreground/60 hover:bg-muted/30"
          >
            <CardContent className="flex flex-col items-center justify-center gap-2 p-0">
              <IconPlus className="size-7 opacity-80" />
              <div className="text-sm font-medium">제품 추가</div>
              <div className="text-xs text-muted-foreground/80">새 제품을 빠르게 등록하세요</div>
            </CardContent>
          </Card>
        </DialogTrigger>

        <DialogContent className="w-full sm:max-w-xl">
          <DialogHeader>
            <DialogTitle>새 제품 추가</DialogTitle>
          </DialogHeader>
          <AddProductForm
            onCancel={() => setOpen(false)}
            onSubmit={(data: ProductSaveDto) => {
              onAdd(data);
              setOpen(false);
            }}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}

function SortableProductCard({ product, onOpen }: { product: ProductDto; onOpen?: (id: number | string) => void }) {
  const { attributes, listeners, setNodeRef, transform, transition, isDragging } = useSortable({ id: product.id });

  const style: React.CSSProperties = {
    transform: CSS.Transform.toString(transform),
    transition,
    cursor: isDragging ? 'grabbing' : 'grab',
    zIndex: isDragging ? 10 : 'auto',
  };

  return (
    <div
      ref={setNodeRef}
      style={style}
      className="group rounded-xl focus-visible:outline-none"
      {...attributes}
      {...listeners}
    >
      <Card className="overflow-hidden transition-shadow hover:shadow-md">
        <div className="relative aspect-[4/3] bg-muted">
          <Image
            src={`http://localhost:8090/api/file/file-download/${product.thumbnailFileId}`}
            alt={product.productNm}
            fill
            sizes="(max-width: 768px) 100vw, 33vw"
            className="object-cover transition-transform duration-200 group-hover:scale-[1.02]"
            priority={false}
          />
          {product.badge && (
            <span className="absolute left-2 top-2 rounded-md bg-primary px-2 py-0.5 text-[11px] font-medium text-primary-foreground">
              {product.badge === 'new' ? '신상' : product.badge === 'sale' ? '세일' : '추천'}
            </span>
          )}
        </div>
        <CardHeader className="pb-2">
          <CardTitle className="line-clamp-2 text-base font-semibold">{product.productNm}</CardTitle>
          <CardDescription className="tabular-nums text-base font-medium text-foreground">
            {product.price.toLocaleString()}원
          </CardDescription>
        </CardHeader>

        {/* 카드 전체 클릭으로 상세 열고 싶다면 아래 버튼/영역 추가 */}
        <CardContent className="pt-0">
          <Button variant="outline" size="sm" className="mt-2" onClick={() => onOpen?.(product.id)}>
            상세 보기
          </Button>
        </CardContent>
      </Card>
    </div>
  );
}

function AddProductForm(props: { onSubmit: (data: ProductSaveDto) => void; onCancel: () => void }) {
  const fileInputRef = useRef<HTMLInputElement | null>(null);
  const [selectedFileName, setSelectedFileName] = useState<string>('');

  return (
    <form
      className="mt-2 space-y-4"
      onSubmit={(e) => {
        e.preventDefault();
        const data = Object.fromEntries(
          new FormData(e.currentTarget as HTMLFormElement).entries(),
        ) as unknown as ProductSaveDto;
        props.onSubmit(data);
      }}
    >
      <div className="space-y-2">
        <Label htmlFor="name">제품명</Label>
        <Input id="productNm" name="productNm" placeholder="예: 스냅 프리미엄" required />
      </div>

      <div className="space-y-2">
        <Label htmlFor="price">가격</Label>
        <Input id="price" name="price" type="number" min={0} placeholder="예: 12900" required />
      </div>

      <div className="space-y-2">
        <Label htmlFor="price">진행시간</Label>
        <Input id="durationHours" name="durationHours" type="number" min={0} placeholder="예: 5시간" required />
      </div>

      <div className="space-y-2">
        <Label htmlFor="description">제품설명</Label>
        <Textarea placeholder="예: 해당 제품은 작가 2명 보조 2명이 참여합니다." name="description" id="description" />
      </div>

      <div className="space-y-2">
        <Label htmlFor="imageUrl">이미지 URL</Label>
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
            업로드
          </Button>
          {selectedFileName && <span className="text-sm text-muted-foreground">{selectedFileName}</span>}
        </div>
      </div>

      <div className="space-y-2">
        <Label htmlFor="useYn">게시여부</Label>
        <RadioGroup
          defaultValue="Y"
          className="flex gap-3"
          name="postingYn"
          onValueChange={(value) => console.log(value)}
        >
          <div className="flex items-center">
            <RadioGroupItem value="Y" id="r1" />
            <Label htmlFor="r1">게시</Label>
          </div>
          <div className="flex items-center">
            <RadioGroupItem value="N" id="r2" />
            <Label htmlFor="r2">미게시</Label>
          </div>
        </RadioGroup>
      </div>

      <div className="space-y-2">
        <Label htmlFor="useYn">사용여부</Label>
        <RadioGroup defaultValue="Y" className="flex gap-3" name="useYn" onValueChange={(value) => console.log(value)}>
          <div className="flex items-center">
            <RadioGroupItem value="Y" id="r1" />
            <Label htmlFor="r1">사용</Label>
          </div>
          <div className="flex items-center">
            <RadioGroupItem value="N" id="r2" />
            <Label htmlFor="r2">미사용</Label>
          </div>
        </RadioGroup>
      </div>

      <div className="space-y-2">
        <Label>배지</Label>
        <Select name="badge">
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
        <Button type="button" variant="outline" onClick={props.onCancel}>
          취소
        </Button>
        <Button type="submit">추가</Button>
      </div>
    </form>
  );
}
