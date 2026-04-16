import { Component } from '@angular/core';
import { MeasurementService } from './services/measurement.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-type-selector',
  standalone: false,
  template: `
    <div class="type-container">
      <h3>Select Measurement Type</h3>
      <div class="card-grid">
        <button 
          class="type-card" 
          [class.active]="(selectedType$ | async) === t.value"
          *ngFor="let t of types"
          (click)="selectType(t.value)">
          {{ t.label }}
        </button>
      </div>
    </div>
  `,
  styles: [`
    .type-container { margin-bottom: 24px; }
    h3 { margin-bottom: 12px; font-weight: 500; color: #e2e8f0; }
    .card-grid { display: flex; gap: 12px; flex-wrap: wrap; }
    .type-card {
      padding: 16px 24px;
      border-radius: 12px;
      border: 1px solid #334155;
      background: #1e293b;
      color: #94a3b8;
      cursor: pointer;
      font-weight: 600;
      transition: all 0.2s ease-in-out;
      flex: 1;
      min-width: 120px;
    }
    .type-card:hover { background: #334155; color: #f8fafc; }
    .type-card.active {
      background: #3b82f6; 
      color: #ffffff;
      border-color: #60a5fa;
      box-shadow: 0 0 15px rgba(59, 130, 246, 0.4);
    }
  `]
})
export class TypeSelectorComponent {
  types = [
    { label: 'Length', value: 'LengthUnit' },
    { label: 'Weight', value: 'WeightUnit' },
    { label: 'Temperature', value: 'TemperatureUnit' },
    { label: 'Volume', value: 'VolumeUnit' }
  ];
  selectedType$: Observable<string>;

  constructor(private measurementService: MeasurementService) {
    this.selectedType$ = this.measurementService.selectedType$;
  }

  selectType(type: string) {
    this.measurementService.setSelectedType(type);
  }
}
