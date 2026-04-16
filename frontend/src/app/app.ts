import { Component, OnInit } from '@angular/core';
import { MeasurementService } from './services/measurement.service';
import { AuthService } from './services/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class App implements OnInit {
  isLoading$: Observable<boolean>;
  errors$: Observable<string>;
  isLoggedIn$: Observable<boolean>;

  constructor(private ms: MeasurementService, private authService: AuthService) {
    this.isLoading$ = this.ms.isLoading$;
    this.errors$ = this.ms.errors$;
    this.isLoggedIn$ = this.authService.isLoggedIn$;
  }

  logout() {
    this.authService.logout();
  }

  ngOnInit() {}
}
