import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: false,
  template: `
    <div class="auth-container">
      <div class="auth-card">
        <h2>{{ isLoginMode ? 'Welcome Back' : 'Create Account' }}</h2>
        <p class="subtitle">{{ isLoginMode ? 'Login to access the dashboard' : 'Sign up to get started' }}</p>

        <form [formGroup]="authForm" (ngSubmit)="onSubmit()">
          <div class="form-group">
            <label>Email</label>
            <input type="email" formControlName="email" placeholder="Enter your email" />
            <div *ngIf="f['email'].invalid && f['email'].touched" class="error-text">
              Valid email is required
            </div>
          </div>
          <div class="form-group">
            <label>Password</label>
            <input type="password" formControlName="password" placeholder="Enter your password" />
            <div *ngIf="f['password'].invalid && f['password'].touched" class="error-text">
              Password must be at least 5 characters
            </div>
          </div>

          <div *ngIf="error" class="error-banner">{{ error }}</div>
          <div *ngIf="successMsg" class="success-banner">{{ successMsg }}</div>

          <button type="submit" [disabled]="isLoading || authForm.invalid" class="submit-btn">
            {{ isLoading ? 'Processing...' : (isLoginMode ? 'Login' : 'Sign Up') }}
          </button>
        </form>

        <p class="toggle-mode">
          {{ isLoginMode ? "Don't have an account?" : "Already have an account?" }}
          <a href="javascript:void(0)" (click)="toggleMode()">
            {{ isLoginMode ? 'Sign Up' : 'Login' }}
          </a>
        </p>
      </div>
    </div>
  `,
  styles: [`
    .auth-container { display: flex; justify-content: center; align-items: center; min-height: 80vh; }
    .auth-card { background: #1e293b; padding: 40px; border-radius: 16px; width: 100%; max-width: 400px; box-shadow: 0 10px 25px rgba(0,0,0,0.5); border: 1px solid #334155; }
    h2 { color: #f8fafc; font-size: 2rem; margin-bottom: 8px; text-align: center; }
    .subtitle { color: #94a3b8; text-align: center; margin-bottom: 32px; }
    .form-group { margin-bottom: 20px; display: flex; flex-direction: column; }
    label { color: #cbd5e1; font-size: 0.875rem; margin-bottom: 8px; }
    input { padding: 12px; border-radius: 8px; border: 1px solid #475569; background: #0f172a; color: #f8fafc; font-size: 1rem; width: 100%; }
    input:focus { outline: none; border-color: #3b82f6; }
    .error-text { color: #ef4444; font-size: 0.75rem; margin-top: 4px; }
    .error-banner { background: rgba(239, 68, 68, 0.1); color: #ef4444; padding: 12px; border-radius: 8px; text-align: center; margin-bottom: 20px; border: 1px solid #ef4444; }
    .success-banner { background: rgba(34, 197, 94, 0.1); color: #22c55e; padding: 12px; border-radius: 8px; text-align: center; margin-bottom: 20px; border: 1px solid #22c55e; }
    .submit-btn { width: 100%; background: #3b82f6; color: white; padding: 12px; border: none; border-radius: 8px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: 0.2s; margin-top: 10px; }
    .submit-btn:hover:not(:disabled) { background: #2563eb; }
    .submit-btn:disabled { opacity: 0.7; cursor: not-allowed; }
    .toggle-mode { margin-top: 24px; text-align: center; color: #94a3b8; font-size: 0.875rem; }
    .toggle-mode a { color: #3b82f6; text-decoration: none; font-weight: 600; }
    .toggle-mode a:hover { text-decoration: underline; }
  `]
})
export class AuthComponent {
  authForm: FormGroup;
  isLoginMode = true;
  isLoading = false;
  error: string | null = null;
  successMsg: string | null = null;

  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.authForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  get f() { return this.authForm.controls; }

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    this.error = null;
    this.successMsg = null;
  }

  onSubmit() {
    if (this.authForm.invalid) return;

    this.isLoading = true;
    this.error = null;
    this.successMsg = null;
    const { email, password } = this.authForm.value;

    if (this.isLoginMode) {
      this.authService.login(email, password).subscribe({
        next: () => {
          this.isLoading = false;
          // Login success → app.html will auto-hide this component via isLoggedIn$
        },
        error: (err) => {
          this.error = err?.message || 'Invalid email or password';
          this.isLoading = false;
        }
      });
    } else {
      this.authService.signup(email, password).subscribe({
        next: () => {
          this.isLoading = false;
          this.successMsg = 'Account created successfully! Please login.';
          this.isLoginMode = true; // Auto-switch to login mode
        },
        error: (err) => {
          this.error = err?.message || 'Signup failed. Please try again.';
          this.isLoading = false;
        }
      });
    }
  }
}