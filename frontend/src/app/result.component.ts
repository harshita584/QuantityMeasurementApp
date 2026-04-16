import { Component } from '@angular/core';
import { MeasurementService } from './services/measurement.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-result',
  standalone: false,
  template: `
    <div class="result-box" *ngIf="result$ | async as result">
      <h4>Result</h4>
      <div class="result-value" *ngIf="(action$ | async) === 'comparison'; else defaultResult">
        {{ result | comparisonSymbol }}
      </div>
      <ng-template #defaultResult>
        <div class="result-value">
           {{ result | unitFormat }}
        </div>
      </ng-template>
    </div>
  `,
  styles: [`
    .result-box {
      background: linear-gradient(145deg, #1e293b, #0f172a);
      padding: 24px; border-radius: 12px; margin-top: 16px; border: 1px solid #334155;
      text-align: center; box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    }
    h4 { color: #94a3b8; font-size: 0.875rem; text-transform: uppercase; margin-bottom: 12px; }
    .result-value { font-size: 2.5rem; font-weight: 700; color: #38bdf8; }
  `]
})
export class ResultComponent {
  result$: Observable<string>;
  action$: Observable<string>;

  constructor(private ms: MeasurementService) {
    this.result$ = this.ms.result$;
    this.action$ = this.ms.selectedAction$;
  }
}
