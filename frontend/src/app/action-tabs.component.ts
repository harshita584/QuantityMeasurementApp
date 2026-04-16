import { Component, Input } from '@angular/core';
import { MeasurementService } from './services/measurement.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-action-tabs',
  standalone: false,
  template: `
    <div class="tabs-container">
      <button 
        class="tab-btn" 
        [class.active]="(selectedAction$ | async) === action.id"
        *ngFor="let action of actions"
        (click)="selectAction(action.id)">
        {{ action.label }}
      </button>
    </div>
  `,
  styles: [`
    .tabs-container {
      display: flex;
      border-bottom: 2px solid #334155;
      margin-bottom: 24px;
    }
    .tab-btn {
      padding: 12px 24px;
      background: transparent;
      border: none;
      color: #94a3b8;
      font-size: 1rem;
      font-weight: 500;
      cursor: pointer;
      border-bottom: 2px solid transparent;
      margin-bottom: -2px;
      transition: all 0.2s;
    }
    .tab-btn:hover { color: #e2e8f0; }
    .tab-btn.active {
      color: #3b82f6;
      border-bottom-color: #3b82f6;
    }
  `]
})
export class ActionTabsComponent {
  actions = [
    { id: 'conversion', label: 'Conversion' },
    { id: 'comparison', label: 'Comparison' },
    { id: 'arithmetic', label: 'Arithmetic' }
  ];
  selectedAction$: Observable<string>;

  constructor(private measurementService: MeasurementService) {
    this.selectedAction$ = this.measurementService.selectedAction$;
  }

  selectAction(actionId: string) {
    this.measurementService.setSelectedAction(actionId);
  }
}
