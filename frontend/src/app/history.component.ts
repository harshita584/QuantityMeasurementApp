import { Component, OnInit, OnDestroy } from '@angular/core';
import { HistoryService } from './services/history.service';
import { HistoryRecord } from './models';

@Component({
  selector: 'app-history',
  standalone: false,
  template: `
    <div class="history-container">
      <h3>History</h3>
      <div class="history-list">
        <div class="history-card" *ngFor="let item of history">
          <div class="meta">
            <span class="badge">{{ item.operation }}</span>
            <span class="badge id-badge">#{{ item.id }}</span>
          </div>
          <div class="details">
            <div class="operands">
              <span>{{ item.operand1 }}</span>
              <span class="op-symbol" *ngIf="item.operand2"> → {{ item.operand2 }}</span>
            </div>
            <div class="result-line" *ngIf="item.result">
              <span class="result-label">Result:</span>
              <span class="result-value">{{ item.result }}</span>
            </div>
            <div class="error-line" *ngIf="item.errorMessage">
              <span>Error: {{ item.errorMessage }}</span>
            </div>
          </div>
        </div>
        <div *ngIf="history.length === 0" class="empty">No recent calculations.</div>
      </div>
    </div>
  `,
  styles: [`
    .history-container { margin-top: 32px; }
    h3 { color: #e2e8f0; margin-bottom: 16px; font-weight: 500; }
    .history-list { display: flex; flex-direction: column; gap: 12px; max-height: 400px; overflow-y: auto; }
    .history-card { background: #1e293b; padding: 16px; border-radius: 8px; border-left: 4px solid #3b82f6; }
    .meta { display: flex; gap: 8px; margin-bottom: 8px; align-items: center; }
    .badge { background: #334155; color: #f8fafc; padding: 4px 8px; border-radius: 4px; font-size: 0.75rem; text-transform: uppercase; }
    .id-badge { background: #1e3a5f; color: #60a5fa; }
    .details { color: #cbd5e1; font-size: 1rem; }
    .operands { margin-bottom: 4px; }
    .op-symbol { color: #94a3b8; }
    .result-line { margin-top: 4px; }
    .result-label { color: #64748b; font-size: 0.875rem; margin-right: 8px; }
    .result-value { color: #38bdf8; font-weight: 600; font-size: 1.1rem; }
    .error-line { color: #f87171; font-size: 0.875rem; margin-top: 4px; }
    .empty { color: #64748b; font-style: italic; }
  `]
})
export class HistoryComponent implements OnInit, OnDestroy {
  history: HistoryRecord[] = [];
  private intervalId: any;

  constructor(private historyService: HistoryService) {}

  ngOnInit() {
    this.refresh();
    // Poll for updates every 5 seconds
    this.intervalId = setInterval(() => this.refresh(), 5000);
  }

  refresh() {
    this.historyService.getHistory().subscribe(h => this.history = h);
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
}
