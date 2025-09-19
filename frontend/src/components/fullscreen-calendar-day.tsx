'use client';

import React from 'react';
import { format, getDay, isEqual, isSameMonth, isToday } from 'date-fns';
import { cn } from '@/lib/utils';
import { CalDataType } from '@/types/calDataType';

interface FullscreenCalendarDayProps {
  day: Date;
  selectedDay: Date;
  setSelectedDay: React.Dispatch<React.SetStateAction<Date>>;
  firstDayCurrentMonth: Date;
  dayIndex: number;
  minHeight: string;
}

const colStartClasses = ['', 'col-start-2', 'col-start-3', 'col-start-4', 'col-start-5', 'col-start-6', 'col-start-7'];

export function FullScreenCalendarDay({
  day,
  selectedDay,
  setSelectedDay,
  firstDayCurrentMonth,
  dayIndex,
  minHeight,
}: FullscreenCalendarDayProps) {
  const handleDayClick = (d: Date) => setSelectedDay(d);

  return (
    <div
      key={dayIndex}
      onClick={() => handleDayClick(day)}
      className={cn(
        'flex flex-col border-r focus:z-10 min-h-28',
        dayIndex === 0 && colStartClasses[getDay(day)],
        !isEqual(day, selectedDay) &&
          !isToday(day) &&
          !isSameMonth(day, firstDayCurrentMonth) &&
          'bg-accent/50 text-muted-foreground',
        !isEqual(day, selectedDay) && 'hover:bg-muted/75',
      )}
      style={{ minHeight }}
    >
      <header className="flex h-9 items-center justify-between px-2">
        <button
          type="button"
          className={cn(
            isEqual(day, selectedDay) && 'text-primary-foreground',
            !isEqual(day, selectedDay) && !isToday(day) && isSameMonth(day, firstDayCurrentMonth) && 'text-foreground',
            !isEqual(day, selectedDay) &&
              !isToday(day) &&
              !isSameMonth(day, firstDayCurrentMonth) &&
              'text-muted-foreground',
            isEqual(day, selectedDay) && isToday(day) && 'border-none bg-primary',
            isEqual(day, selectedDay) && !isToday(day) && 'bg-foreground text-background',
            (isEqual(day, selectedDay) || isToday(day)) && 'font-semibold',
            'flex h-7 w-7 items-center justify-center rounded-full text-xs hover:border',
          )}
        >
          <time dateTime={format(day, 'yyyy-MM-dd')}>{format(day, 'd')}</time>
        </button>
      </header>

      <div className="h-full w-full px-1 pb-2" />
    </div>
  );
}
