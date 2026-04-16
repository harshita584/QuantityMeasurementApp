import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MeasurementService } from './services/measurement.service';
import { ConversionService } from './services/conversion.service';
import { HistoryService } from './services/history.service';
import { Subscription, combineLatest, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, tap, catchError, map, filter } from 'rxjs/operators';
import { Unit, CalculationRequest } from './models';

@Component({
  selector: 'app-unit-input',
  standalone: false,
  template: `
    <form [formGroup]="form" class="form-container">
      <div class="row">
        <div class="form-group">
          <label>From Value</label>
          <input type="number" formControlName="fromValue" placeholder="Enter value">
        </div>
        <div class="form-group">
          <label>From Unit</label>
          <select formControlName="fromUnit">
            <option disabled value="">Select unit</option>
            <option *ngFor="let u of units" [value]="u.label">{{ u.label }}</option>
          </select>
        </div>
      </div>

      <app-operator 
        [show]="currentAction === 'arithmetic'"
        [selectedOperator]="form.get('operator')?.value"
        (operatorChange)="form.get('operator')?.setValue($event)">
      </app-operator>

      <div class="row" *ngIf="currentAction !== 'conversion'">
        <div class="form-group">
          <label>To Value</label>
          <input type="number" formControlName="toValue" placeholder="Enter value">
        </div>
        <div class="form-group">
          <label>To Unit</label>
          <select formControlName="toUnit">
            <option disabled value="">Select unit</option>
            <option *ngFor="let u of units" [value]="u.label">{{ u.label }}</option>
          </select>
        </div>
      </div>
      
      <div class="row" *ngIf="currentAction === 'conversion'">
        <div class="form-group empty"></div>
        <div class="form-group">
          <label>To Unit</label>
          <select formControlName="toUnit">
            <option disabled value="">Select unit</option>
            <option *ngFor="let u of units" [value]="u.label">{{ u.label }}</option>
          </select>
        </div>
      </div>
    </form>

    <div *ngIf="form.invalid && form.dirty" class="validation-msg">
      Please provide valid numbers and select options.
    </div>
  `,
  styles: [`
    .form-container { display: flex; flex-direction: column; gap: 16px; margin-bottom: 24px; }
    .row { display: flex; gap: 16px; align-items: flex-end; }
    .form-group { display: flex; flex-direction: column; flex: 1; }
    .form-group.empty { flex: 1; }
    label { color: #cbd5e1; font-size: 0.875rem; margin-bottom: 8px; }
    input, select {
      padding: 12px; border-radius: 8px; border: 1px solid #475569;
      background: #1e293b; color: #f8fafc; font-size: 1rem; width: 100%;
    }
    input:focus, select:focus { outline: none; border-color: #3b82f6; }
    .validation-msg { color: #f87171; font-size: 0.875rem; margin-top: -12px; margin-bottom: 16px; }
  `]
})
export class UnitInputComponent implements OnInit, OnDestroy {
  form: FormGroup;
  units: Unit[] = [];
  currentType = '';
  currentAction = '';
  private subs = new Subscription();

  constructor(
    private fb: FormBuilder,
    private measurementService: MeasurementService,
    private conversionService: ConversionService,
    private historyService: HistoryService
  ) {
    this.form = this.fb.group({
      fromValue: [null, [Validators.required, Validators.pattern(/^-?\d*\.?\d+$/)]],
      fromUnit: ['', Validators.required],
      toValue: [null],
      toUnit: ['', Validators.required],
      operator: ['']
    });
  }

  ngOnInit() {
    this.subs.add(
      combineLatest([
        this.measurementService.selectedType$.pipe(distinctUntilChanged()),
        this.measurementService.selectedAction$.pipe(distinctUntilChanged())
      ]).subscribe(([type, action]) => {
        this.currentType = type;
        this.currentAction = action;
        
        // Reset form except operator if switching between actions on same type
        this.form.reset({
          fromValue: null,
          fromUnit: '',
          toValue: null,
          toUnit: '',
          operator: action === 'arithmetic' ? '+' : ''
        });

        this.updateValidators();

        if (type) {
          this.fetchUnits(type);
        } else {
          this.units = [];
        }
      })
    );

    this.subs.add(
      this.form.valueChanges.pipe(
        debounceTime(300),
        tap(() => this.measurementService.clearError())
      ).subscribe(val => {
        if (this.currentType && this.form.valid) {
          this.calculate(val);
        } else {
          this.measurementService.clearResult();
        }
      })
    );
  }

  updateValidators() {
    const toValueCtrl = this.form.get('toValue');
    const operatorCtrl = this.form.get('operator');

    if (this.currentAction === 'conversion') {
      toValueCtrl?.clearValidators();
      operatorCtrl?.clearValidators();
    } else if (this.currentAction === 'comparison') {
      toValueCtrl?.setValidators([Validators.required, Validators.pattern(/^-?\d*\.?\d+$/)]);
      operatorCtrl?.clearValidators();
    } else if (this.currentAction === 'arithmetic') {
      toValueCtrl?.setValidators([Validators.required, Validators.pattern(/^-?\d*\.?\d+$/)]);
      operatorCtrl?.setValidators([Validators.required]);
    }

    toValueCtrl?.updateValueAndValidity({ emitEvent: false });
    operatorCtrl?.updateValueAndValidity({ emitEvent: false });
  }

  fetchUnits(type: string) {
    this.measurementService.setLoading(true);
    this.conversionService.getUnits(type).pipe(
      catchError(err => {
        this.measurementService.setError(err.message || 'Failed to fetch units');
        return of([]);
      })
    ).subscribe(data => {
      this.units = data;
      this.measurementService.setLoading(false);
    });
  }

  calculate(formValue: any) {
    const req: CalculationRequest = {
      fromValue: Number(formValue.fromValue),
      fromUnit: formValue.fromUnit,
      toValue: formValue.toValue ? Number(formValue.toValue) : undefined,
      toUnit: formValue.toUnit,
      actionType: this.currentAction,
      operator: formValue.operator
    };

    this.measurementService.setLoading(true);
    this.conversionService.processCalculation(this.currentType, req).subscribe({
      next: (res) => {
        this.measurementService.setResult(res);
        this.measurementService.setLoading(false);
        // Save to history
        this.historyService.save({
          type: this.currentType,
          action: this.currentAction,
          result: res,
          request: req
        }).subscribe();
      },
      error: (err) => {
        this.measurementService.setLoading(false);
        // Error intercepted by global interceptor, but we can also set local
        this.measurementService.setError(err.message);
      }
    });
  }

  ngOnDestroy() {
    this.subs.unsubscribe();
  }
}
