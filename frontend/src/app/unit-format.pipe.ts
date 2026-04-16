import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'unitFormat',
  standalone: false
})
export class UnitFormatPipe implements PipeTransform {
  transform(value: number | string | null | undefined): string {
    if (value === null || value === undefined || value === '') return '';
    const num = Number(value);
    if (isNaN(num)) return String(value);

    // Format with commas and max 4 decimal digits
    return new Intl.NumberFormat('en-US', { maximumFractionDigits: 4 }).format(num);
  }
}
