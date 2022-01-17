import { Pipe, PipeTransform } from '@angular/core';

import * as dayjs from 'dayjs';

@Pipe({
  name: 'duration',
})
export class DurationPipe implements PipeTransform {
  transform(value: Partial<{ milliseconds: number; seconds: number; minutes: number; hours: number; days: number; months: number; years: number; weeks: number; }>): string {
    return dayjs.duration(value).humanize();
  }
}
