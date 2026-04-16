import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MeasurementService } from '../services/measurement.service';

@Injectable()
export class GlobalErrorInterceptor implements HttpInterceptor {
  constructor(private measurementService: MeasurementService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Skip auth endpoints — they have their own error handling in auth.service.ts
    if (req.url.includes('/api/v1/auth/')) {
      return next.handle(req);
    }

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMsg = 'An unknown error occurred!';
        if (error.error instanceof ErrorEvent) {
          // Client-side mapping
          errorMsg = `Error: ${error.error.message}`;
        } else {
          // Server-side mapping
          errorMsg = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        this.measurementService.setError(errorMsg);
        return throwError(() => new Error(errorMsg));
      })
    );
  }
}
