import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Unit, CalculationRequest } from '../models';
import { AuthService } from './auth.service';

// Unit labels aligned with backend enum names exactly
const UNITS_DB: { [type: string]: Unit[] } = {
  LengthUnit: [
    { id: 1, label: 'FEET', symbol: 'ft' },
    { id: 2, label: 'INCHES', symbol: 'in' },
    { id: 3, label: 'YARDS', symbol: 'yd' },
    { id: 4, label: 'CENTIMETERS', symbol: 'cm' }
  ],
  WeightUnit: [
    { id: 5, label: 'GRAM', symbol: 'g' },
    { id: 6, label: 'KILOGRAM', symbol: 'kg' },
    { id: 7, label: 'TONNE', symbol: 't' }
  ],
  VolumeUnit: [
    { id: 8, label: 'LITRE', symbol: 'l' },
    { id: 9, label: 'GALLON', symbol: 'gal' },
    { id: 10, label: 'MILLILITRE', symbol: 'ml' }
  ],
  TemperatureUnit: [
    { id: 11, label: 'CELSIUS', symbol: '°C' },
    { id: 12, label: 'FAHRENHEIT', symbol: '°F' },
    { id: 13, label: 'KELVIN', symbol: 'K' }
  ]
};

@Injectable({
  providedIn: 'root'
})
export class ConversionService {
  private apiUrl = 'http://localhost:8080/api/v1/quantities';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUnits(type: string): Observable<Unit[]> {
    if (!type) return of([]);
    const units = UNITS_DB[type];
    if (units) {
      return of(units);
    }
    return throwError(() => new Error(`Units for type ${type} not found`));
  }

  processCalculation(type: string, req: CalculationRequest): Observable<string> {
    const userId = this.authService.getUserId();

    if (req.actionType === 'conversion') {
      // Backend: POST /api/v1/quantities/convert/{targetUnit}
      // Body: { value, unit } (single QuantityDTO)
      // Returns: { value, unit } (QuantityDTO)
      const body = { value: req.fromValue, unit: req.fromUnit };
      const targetUnit = req.toUnit;
      return this.http.post<any>(`${this.apiUrl}/convert/${targetUnit}?userId=${userId}`, body).pipe(
        map(res => String(res.value))
      );

    } else if (req.actionType === 'comparison') {
      // Backend: POST /api/v1/quantities/compare
      // Body: { thisQuantityDTO: {value, unit}, thatQuantityDTO: {value, unit} }
      // Returns: raw boolean (true/false)
      const body = this.buildInputPayload(req);
      return this.http.post(`${this.apiUrl}/compare?userId=${userId}`, body, { responseType: 'text' }).pipe(
        map(res => {
          return res === 'true' ? 'equals' : 'not equals';
        })
      );

    } else if (req.actionType === 'arithmetic') {
      // Backend: POST /api/v1/quantities/add, /subtract, /divide
      // Body: { thisQuantityDTO: {value, unit}, thatQuantityDTO: {value, unit} }
      let endpoint = '';
      if (req.operator === '+') endpoint = '/add';
      else if (req.operator === '-') endpoint = '/subtract';
      else if (req.operator === '/') endpoint = '/divide';

      const body = this.buildInputPayload(req);

      if (req.operator === '/') {
        // Divide returns raw double
        return this.http.post(`${this.apiUrl}${endpoint}?userId=${userId}`, body, { responseType: 'text' }).pipe(
          map(res => String(res))
        );
      } else {
        // Add/Subtract returns QuantityDTO { value, unit }
        return this.http.post<any>(`${this.apiUrl}${endpoint}?userId=${userId}`, body).pipe(
          map(res => String(res.value))
        );
      }
    }

    return throwError(() => new Error(`Unknown action type: ${req.actionType}`));
  }

  /**
   * Build the QuantityInputDTO payload matching the backend:
   * { thisQuantityDTO: { value, unit }, thatQuantityDTO: { value, unit } }
   */
  private buildInputPayload(req: CalculationRequest): any {
    return {
      thisQuantityDTO: {
        value: req.fromValue,
        unit: req.fromUnit
      },
      thatQuantityDTO: {
        value: req.toValue || 0,
        unit: req.toUnit
      }
    };
  }
}
