import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {
  private selectedTypeSource = new BehaviorSubject<string>('');
  selectedType$ = this.selectedTypeSource.asObservable();

  private selectedActionSource = new BehaviorSubject<string>('conversion');
  selectedAction$ = this.selectedActionSource.asObservable();

  private resultSource = new BehaviorSubject<string>('');
  result$ = this.resultSource.asObservable();

  private isLoadingSource = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSource.asObservable();

  private errorsSource = new BehaviorSubject<string>('');
  errors$ = this.errorsSource.asObservable();

  constructor() {}

  setSelectedType(type: string) {
    this.selectedTypeSource.next(type);
    this.clearResult();
  }

  setSelectedAction(action: string) {
    this.selectedActionSource.next(action);
    this.clearResult();
  }

  setResult(result: string) {
    this.resultSource.next(result);
  }

  clearResult() {
    this.resultSource.next('');
  }

  setLoading(isLoading: boolean) {
    this.isLoadingSource.next(isLoading);
  }

  setError(error: string) {
    this.errorsSource.next(error);
  }
  
  clearError() {
    this.errorsSource.next('');
  }
}
