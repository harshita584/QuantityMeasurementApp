import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  // History lives in user-service, routed via API Gateway
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient, private authService: AuthService) {}

  /**
   * Fetch history for the current user.
   * Backend: GET /api/users/{userId}/history
   * Returns: QuantityMeasurementEntity[]
   */
  getHistory(): Observable<any[]> {
    const userId = this.authService.getUserId();
    return this.http.get<any[]>(`${this.apiUrl}/${userId}/history`).pipe(
      catchError(() => of([]))  // Return empty array on error (e.g. user-service down)
    );
  }

  /**
   * History saving is handled automatically by the measurement-service
   * when calculation endpoints are called (via Feign to user-service).
   * This is a no-op on the frontend side.
   */
  save(record: any): Observable<any> {
    return of({});
  }
}
