import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'comparisonSymbol',
  standalone: false
})
export class ComparisonSymbolPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) return '';
    if (value === 'greater') return '>';
    if (value === 'less') return '<';
    if (value === 'equals') return '=';
    return value;
  }
}
