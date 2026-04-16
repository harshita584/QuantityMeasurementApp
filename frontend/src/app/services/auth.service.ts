import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';

  private loggedInSource = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this.loggedInSource.asObservable();

  private currentUserSource = new BehaviorSubject<any>(null);
  currentUser$ = this.currentUserSource.asObservable();

  constructor(private http: HttpClient) {
    const savedToken = localStorage.getItem('qm_user_token');
    if (savedToken) {
      this.loggedInSource.next(true);
      // Token is the userId returned by the backend
      this.currentUserSource.next({ id: Number(savedToken) });
    }
  }

  /** Returns the current userId (stored as the token) */
  getUserId(): number {
    const token = localStorage.getItem('qm_user_token');
    return token ? Number(token) : 1;
  }

  login(email: string, pass: string): Observable<string> {
    return this.http.post(`${this.apiUrl}/login`, { email, password: pass }, { responseType: 'text' }).pipe(
      tap((token: string) => {
        this.setSession(token, email);
      }),
      catchError(err => {
        const msg = typeof err.error === 'string' ? err.error : 'Invalid email or password';
        return throwError(() => new Error(msg));
      })
    );
  }

  signup(email: string, pass: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/signup`, { email, password: pass }).pipe(
      tap(() => {
        // After signup, user needs to login separately
      }),
      catchError(err => {
        const msg = err.error?.message || 'Email already registered or invalid payload';
        return throwError(() => new Error(msg));
      })
    );
  }

  logout() {
    this.loggedInSource.next(false);
    this.currentUserSource.next(null);
    localStorage.removeItem('qm_user_token');
  }

  private setSession(token: string, email: string) {
    this.loggedInSource.next(true);
    this.currentUserSource.next({ id: Number(token), email });
    localStorage.setItem('qm_user_token', token);
  }
}
