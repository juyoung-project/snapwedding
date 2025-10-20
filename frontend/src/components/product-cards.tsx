'use client';

import Image from 'next/image';
import { useMemo } from 'react';
import { closestCenter, DndContext, DragEndEvent, PointerSensor, useSensor, useSensors } from '@dnd-kit/core';
import { arrayMove, rectSortingStrategy, SortableContext, useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import { IconPlus } from '@tabler/icons-react';

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { ProductDto, ProductOrderDto } from '@/types/domain/product';

type ProductGridSortableProps = {
  products: ProductDto[];
  onReorder: (next: ProductDto[], orderList: ProductOrderDto[]) => void;
  onOpen: (id: string | number) => void;
  onAddClick: () => void; // onAdd에서 onAddClick으로 변경
};

export function ProductGridSortable({ products, onReorder, onOpen, onAddClick }: ProductGridSortableProps) {
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

      {/* "제품 추가" 카드는 이제 onAddClick 핸들러를 호출합니다. */}
      <Card
        role="button"
        aria-label="제품 추가"
        onClick={onAddClick}
        className="flex min-h-[220px] items-center justify-center rounded-xl border-2 border-dashed border-muted-foreground/30 bg-muted/20 p-4 text-muted-foreground transition-colors hover:border-muted-foreground/60 hover:bg-muted/30"
      >
        <CardContent className="flex flex-col items-center justify-center gap-2 p-0">
          <IconPlus className="size-7 opacity-80" />
          <div className="text-sm font-medium">제품 추가</div>
          <div className="text-xs text-muted-foreground/80">새 제품을 빠르게 등록하세요</div>
        </CardContent>
      </Card>
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

        <CardContent className="pt-0">
          <Button variant="outline" size="sm" className="mt-2" onClick={() => onOpen?.(product.id)}>
            상세 보기
          </Button>
        </CardContent>
      </Card>
    </div>
  );
}
