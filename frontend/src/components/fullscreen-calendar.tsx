'use client';

import React from 'react';
import { add, eachDayOfInterval, endOfMonth, endOfWeek, format, parse, startOfToday, startOfWeek } from 'date-fns';
import { FullScreenCalendarHeader } from '@/components/fullscreen-calendar-header';
import { FullScreenCalendarWeekDays } from '@/components/fullscreen-calendar-week-days';
import { FullScreenCalendarWeekRow } from '@/components/fullscreen-calendar-week-row';

// Dummy data
const data = [
  {
    id: 1,
    startDay: new Date('2025-09-18'),
    endDay: new Date('2025-09-20'),
    name: '[테스트] 예약 정보',
    time: '3:00 PM',
  },
  {
    id: 2,
    startDay: new Date('2025-09-18'),
    endDay: new Date('2025-09-18'),
    name: '[테스트] 예약 정보2',
    time: '3:00 PM',
  },
  {
    id: 3,
    startDay: new Date('2025-09-19'),
    endDay: new Date('2025-09-25'),
    name: '[스냅] 홍길동 고객',
    time: '1:00 PM',
  },
  { id: 4, startDay: new Date('2025-08-30'), endDay: new Date('2025-09-30'), name: 'xptleneck', time: '1:00 PM' },
];

export function FullScreenCalendar() {
  const today = startOfToday();
  const [selectedDay, setSelectedDay] = React.useState(today);
  const [currentMonth, setCurrentMonth] = React.useState(format(today, 'MMM-yyyy'));
  const firstDayCurrentMonth = parse(currentMonth, 'MMM-yyyy', new Date());

  const days = React.useMemo(() => {
    return eachDayOfInterval({
      start: startOfWeek(firstDayCurrentMonth),
      end: endOfWeek(endOfMonth(firstDayCurrentMonth)),
    });
  }, [firstDayCurrentMonth]);

  const weeks: Date[][] = React.useMemo(() => {
    const chunks: Date[][] = [];
    for (let i = 0; i < days.length; i += 7) {
      chunks.push(days.slice(i, i + 7));
    }
    return chunks;
  }, [days]);

  function previousMonth() {
    const firstDayPrevMonth = add(firstDayCurrentMonth, { months: -1 });
    setCurrentMonth(format(firstDayPrevMonth, 'MMM-yyyy'));
  }

  function nextMonth() {
    const firstDayNextMonth = add(firstDayCurrentMonth, { months: 1 });
    setCurrentMonth(format(firstDayNextMonth, 'MMM-yyyy'));
  }

  function goToToday() {
    setCurrentMonth(format(today, 'MMM-yyyy'));
  }

  return (
    <div className="flex flex-1 flex-col">
      <FullScreenCalendarHeader
        startOfMonth={firstDayCurrentMonth}
        endOfMonth={endOfMonth(firstDayCurrentMonth)}
        nextMonth={nextMonth}
        previousMonth={previousMonth}
        goToToday={goToToday}
      />
      <div className="lg:flex lg:flex-auto lg:flex-col">
        <FullScreenCalendarWeekDays />
        {/* Desktop */}
        <div className="hidden w-full border-x lg:flex lg:flex-col">
          {weeks.map((week, idx) => (
            <FullScreenCalendarWeekRow
              key={`${currentMonth}-${idx}`}
              days={week}
              selectedDay={selectedDay}
              setSelectedDay={setSelectedDay}
              firstDayCurrentMonth={firstDayCurrentMonth}
              events={data}
            />
          ))}
        </div>
        {/* Mobile */}
        <div className="isolate grid w-full grid-cols-1 border-x lg:hidden">
          {weeks.map((week, idx) => (
            <FullScreenCalendarWeekRow
              key={`${currentMonth}-m-${idx}`}
              days={week}
              selectedDay={selectedDay}
              setSelectedDay={setSelectedDay}
              firstDayCurrentMonth={firstDayCurrentMonth}
              events={data}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
