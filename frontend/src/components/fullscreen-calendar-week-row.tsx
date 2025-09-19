import React from 'react';
import { FullScreenCalendarDay } from '@/components/fullscreen-calendar-day';
import { FullScreenCalendarWeekOverlay } from '@/components/fullscreen-calendar-week-overlay';
import { CalDataType } from '@/types/calDataType';
import { eachDayOfInterval, endOfMonth, format, max, min, startOfMonth, startOfWeek } from 'date-fns';

export function FullScreenCalendarWeekRow({
  days,
  selectedDay,
  setSelectedDay,
  firstDayCurrentMonth,
  events,
}: {
  days: Date[];
  selectedDay: Date;
  setSelectedDay: React.Dispatch<React.SetStateAction<Date>>;
  firstDayCurrentMonth: Date;
  events: CalDataType[];
}) {
  // 현재 달 범위로 제한하여(클리핑) 겹침 계산
  const minHeight = React.useMemo(() => {
    const monthStart = startOfMonth(firstDayCurrentMonth);
    const monthEnd = endOfMonth(firstDayCurrentMonth);

    // 날짜별 겹침 수
    const counts: Record<string, number> = {};

    events.forEach((evt) => {
      // 이벤트 기간을 현재 달 범위로 클리핑
      const clampedStart = max([evt.startDay, monthStart]);
      const clampedEnd = min([evt.endDay, monthEnd]);
      if (clampedStart > clampedEnd) return; // 현재 달과 겹치지 않음

      const intervalDays = eachDayOfInterval({ start: clampedStart, end: clampedEnd });
      intervalDays.forEach((d) => {
        const key = format(d, 'yyyy-MM-dd');
        counts[key] = (counts[key] || 0) + 1;
      });
    });

    // 주차별 "중복된 날짜 수(겹침 수 >= 2 인 날짜 수)" 계산
    const weeklyDupDays: Record<string, number> = {};
    for (const [dateStr, cnt] of Object.entries(counts)) {
      const [y, m, dd] = dateStr.split('-').map(Number);
      const d = new Date(y, m - 1, dd);
      const weekStart = startOfWeek(d, { weekStartsOn: 1 }); // 월요일 시작
      const weekKey = format(weekStart, 'yyyy-MM-dd');

      if (cnt >= 2) {
        weeklyDupDays[weekKey] = (weeklyDupDays[weekKey] || 0) + 1;
      }
    }

    const monthDupMax = Object.keys(weeklyDupDays).length > 0 ? Math.max(...Object.values(weeklyDupDays)) : 0;

    // 동일 달의 모든 주가 같은 기준 높이를 쓰도록 고정
    return `calc(var(--spacing) * ${monthDupMax === 0 ? 28 : 11 * monthDupMax})`;
  }, [events, firstDayCurrentMonth]);

  return (
    <div className="relative grid grid-cols-7 border-b min-h-28">
      {days.map((day, dayIdx) => (
        <FullScreenCalendarDay
          key={dayIdx}
          day={day}
          selectedDay={selectedDay}
          setSelectedDay={setSelectedDay}
          firstDayCurrentMonth={firstDayCurrentMonth}
          dayIndex={dayIdx}
          minHeight={minHeight}
        />
      ))}
      <FullScreenCalendarWeekOverlay weekStart={days[0]} events={events} />
    </div>
  );
}
