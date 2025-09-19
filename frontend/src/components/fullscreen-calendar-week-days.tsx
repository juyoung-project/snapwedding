import React from 'react';

export function FullScreenCalendarWeekDays() {
  return (
    <div className="grid grid-cols-7 border text-center text-xs font-semibold leading-6 lg:flex-none">
      <div className="border-r py-2.5">일</div>
      <div className="border-r py-2.5">월</div>
      <div className="border-r py-2.5">화</div>
      <div className="border-r py-2.5">수</div>
      <div className="border-r py-2.5">목</div>
      <div className="border-r py-2.5">금</div>
      <div className="py-2.5">토</div>
    </div>
  );
}
