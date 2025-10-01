import { apiGet, apiPut, apiUpload } from '@/lib/commonApi';
import { NextResponse } from 'next/server';

export async function GET() {
  const response = await apiGet('/api/expert-products');
  console.log(response);
  return NextResponse.json(response?.data);
}

export async function POST(req: Request) {
  const payload = await req.formData();
  const response = await apiUpload('/api/expert-products', payload);
  return NextResponse.json(response);
}

export async function PUT(req: Request) {
  const { searchParams } = new URL(req.url);
  const type = searchParams.get('type'); // 'order' | 'fields'

  if (type === 'order') {
    // 순서 변경: JSON PUT
    const payload = await req.json();
    const response = await apiPut('/api/expert-products/order', payload);
    return NextResponse.json(response?.data);
  }
  return NextResponse.json({ message: 'type 쿼리 파라미터가 필요합니다. (order | fields)' }, { status: 400 });
}

export async function DELETE() {}
