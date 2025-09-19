import { differenceInCalendarDays, endOfWeek, format } from 'date-fns';
import { useMemo } from 'react';
import { CalDataType } from '@/types/calDataType';

export function FullScreenCalendarWeekOverlay({ weekStart, events }: { weekStart: Date; events: CalDataType[] }) {
  const weekEnd = endOfWeek(weekStart);

  const weekEvents = useMemo(
    () =>
      events
        .filter((e) => e.startDay <= weekEnd && e.endDay >= weekStart)
        .sort((a, b) => {
          const sa = a.startDay.getTime();
          const sb = b.startDay.getTime();
          if (sa !== sb) return sa - sb;
          const ea = a.endDay.getTime();
          const eb = b.endDay.getTime();
          if (ea !== eb) return ea - eb;
          return a.name.localeCompare(b.name);
        }),
    [events, weekStart, weekEnd],
  );
  return (
    // 날짜 헤더(h-9) 바로 아래에서, 주 박스 안쪽 1px로 시작
    <div className="pointer-events-none absolute inset-x-px top-9 bottom-px">
      <div className="grid grid-cols-7 [grid-auto-rows:minmax(24px,auto)] gap-x-0 gap-y-1">
        {weekEvents.map((ev) => {
          const s = ev.startDay < weekStart ? weekStart : ev.startDay;
          const e = ev.endDay > weekEnd ? weekEnd : ev.endDay;

          const startCol = differenceInCalendarDays(s, weekStart) + 1;
          const span = Math.max(1, differenceInCalendarDays(e, s) + 1);

          const isClippedLeft = s > weekStart;
          const isClippedRight = e < weekEnd;

          return (
            <div
              key={ev.id}
              className="pointer-events-auto"
              style={{ gridColumn: `${startCol} / span ${span}` }}
              title={`${format(ev.startDay, 'MM/dd')} → ${format(ev.endDay, 'MM/dd')} · ${ev.time}`}
            >
              <div
                className={[
                  'flex h-6 items-center gap-2 overflow-hidden px-2 text-[11px] leading-none',
                  'border bg-gray-400/10 text-primary border-gray-400/30',
                  isClippedLeft ? 'rounded-l-none' : 'rounded-l-md',
                  isClippedRight ? 'rounded-r-none' : 'rounded-r-md',
                ].join(' ')}
              >
                {isClippedLeft && <span className="mr-1 text-primary/70">⋯</span>}
                <p className="font-medium truncate">{ev.name}</p>
                <span className="text-muted-foreground/80 truncate">· {ev.time}</span>
                {isClippedRight && <span className="ml-auto text-primary/70">⋯</span>}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
