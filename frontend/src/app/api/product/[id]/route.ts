import { NextRequest, NextResponse } from 'next/server';
import { apiDelete, apiGet, apiPut, apiPutUpload } from '@/lib/commonApi';

// "GET /api/product/[id]" 요청을 처리합니다.
export async function GET(req: NextRequest, { params }: { params: { id: string } }) {
  const { id } = params;

  if (!id) {
    return NextResponse.json({ message: 'ID가 필요합니다.' }, { status: 400 });
  }

  const response = await apiGet(`/api/expert-products/${id}`);
  return NextResponse.json(response?.data);
}

// 여기에 PUT, PATCH, DELETE 핸들러를 추가하여
// 특정 id를 가진 제품을 수정하거나 삭제할 수 있습니다.
export async function PUT(req: NextRequest, { params }: { params: { id: string } }) {
  const { id } = params;
  const payload = await req.formData();
  const response = await apiPutUpload(`/api/expert-products/${id}`, payload);
  return NextResponse.json(response?.data);
}

export async function DELETE(req: NextRequest, { params }: { params: { id: string } }) {
  const { id } = params;

  if (!id) {
    return NextResponse.json({ message: 'ID가 필요합니다.' }, { status: 400 });
  }

  const response = await apiDelete(`/api/expert-products/${id}`);
  return NextResponse.json(response?.data);
}
