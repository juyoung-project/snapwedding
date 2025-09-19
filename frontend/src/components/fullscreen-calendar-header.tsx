'use client';

import React from 'react';
import { format, startOfToday } from 'date-fns';
import { ko } from 'date-fns/locale';
import { ChevronLeftIcon, ChevronRightIcon, PlusCircleIcon } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { Separator } from '@/components/ui/separator';
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';

interface FullScreenCalendarHeaderProps {
  startOfMonth: Date;
  endOfMonth: Date;
  nextMonth: () => void;
  goToToday: () => void;
  previousMonth: () => void;
}

export function FullScreenCalendarHeader({
  nextMonth,
  goToToday,
  previousMonth,
  startOfMonth,
  endOfMonth,
}: FullScreenCalendarHeaderProps) {
  const today = startOfToday();

  return (
    <div className="flex flex-col space-y-4 p-4 md:flex-row md:items-center md:justify-between md:space-y-0 lg:flex-none">
      <div className="flex flex-auto">
        <div className="flex items-center gap-4">
          <div className="hidden w-20 flex-col items-center justify-center rounded-lg border bg-muted p-0.5 md:flex">
            <h1 className="p-1 text-xs uppercase text-muted-foreground">{format(today, 'MMM', { locale: ko })}</h1>
            <div className="flex w-full items-center justify-center rounded-lg border bg-background p-0.5 text-lg font-bold">
              <span>{format(today, 'd', { locale: ko })}</span>
            </div>
          </div>
          <div className="flex flex-col">
            <h2 className="text-lg font-semibold text-foreground">
              {format(startOfMonth, 'yyyy MMMM', { locale: ko })}
            </h2>
            <p className="text-sm text-muted-foreground">
              {format(startOfMonth, 'yyyy MMM d', { locale: ko })} - {format(endOfMonth, 'yyyy MMM d', { locale: ko })}
            </p>
          </div>
        </div>
      </div>

      <div className="flex flex-col items-center gap-4 md:flex-row md:gap-6">
        <Separator orientation="vertical" className="hidden h-6 lg:block" />

        <div className="inline-flex w-full -space-x-px rounded-lg shadow-sm shadow-black/5 md:w-auto rtl:space-x-reverse">
          <Button
            onClick={previousMonth}
            className="rounded-none shadow-none first:rounded-s-lg last:rounded-e-lg focus-visible:z-10"
            variant="outline"
            size="icon"
            aria-label="Navigate to previous month"
          >
            <ChevronLeftIcon size={16} strokeWidth={2} aria-hidden="true" />
          </Button>
          <Button
            onClick={goToToday}
            className="w-full rounded-none shadow-none first:rounded-s-lg last:rounded-e-lg focus-visible:z-10 md:w-auto"
            variant="outline"
          >
            Today
          </Button>
          <Button
            onClick={nextMonth}
            className="rounded-none shadow-none first:rounded-s-lg last:rounded-e-lg focus-visible:z-10"
            variant="outline"
            size="icon"
            aria-label="Navigate to next month"
          >
            <ChevronRightIcon size={16} strokeWidth={2} aria-hidden="true" />
          </Button>
        </div>

        <Separator orientation="vertical" className="hidden h-6 md:block" />

        <Separator orientation="horizontal" className="block w-full md:hidden" />

        <Dialog>
          <form>
            <DialogTrigger asChild>
              <Button className="w-full gap-2 md:w-auto">
                <PlusCircleIcon size={16} strokeWidth={2} aria-hidden="true" />
                <span>일정생성하기</span>
              </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>일정관리</DialogTitle>
                <DialogDescription>일정을 생성하거나 수정하세요</DialogDescription>
              </DialogHeader>

              <div className="grid gap-4">
                <div className="grid gap-3">
                  <Label htmlFor="name-1">제목</Label>
                  <Input id="name-1" name="name" defaultValue="Pedro Duarte" />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="username-1">일정내용</Label>
                  <Input id="username-1" name="username" defaultValue="@peduarte" />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="username-1">일정시작일</Label>
                  <Input id="username-1" name="username" defaultValue="@peduarte" />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="username-1">일정종료일</Label>
                  <Input id="username-1" name="username" defaultValue="@peduarte" />
                </div>
              </div>

              <DialogFooter>
                <DialogClose asChild>
                  <Button type="button" variant="outline">
                    취소
                  </Button>
                </DialogClose>
                <Button type="submit">저장</Button>
              </DialogFooter>
            </DialogContent>
          </form>
        </Dialog>
      </div>
    </div>
  );
}
