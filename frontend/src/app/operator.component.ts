import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-operator',
  standalone: false,
  template: `
    <div class="operator-group" *ngIf="show">
      <button 
        *ngFor="let op of operators" 
        class="op-btn" 
        [class.active]="selectedOperator === op"
        (click)="select(op)">
        {{ op }}
      </button>
    </div>
  `,
  styles: [`
    .operator-group { display: flex; gap: 8px; justify-content: center; margin-bottom: 16px; }
    .op-btn {
      width: 40px; height: 40px;
      border-radius: 8px;
      border: 1px solid #475569;
      background: #1e293b; color: #e2e8f0;
      font-size: 1.2rem; cursor: pointer; transition: 0.2s;
    }
    .op-btn:hover { background: #334155; }
    .op-btn.active { background: #3b82f6; color: white; border-color: #60a5fa; }
  `]
})
export class OperatorComponent {
  @Input() show = false;
  @Input() selectedOperator: string = '';
  @Output() operatorChange = new EventEmitter<string>();

  operators = ['+', '-', '*', '/'];

  select(op: string) {
    this.operatorChange.emit(op);
  }
}
