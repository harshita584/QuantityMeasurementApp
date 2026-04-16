import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { App } from './app';
import { TypeSelectorComponent } from './type-selector.component';
import { ActionTabsComponent } from './action-tabs.component';
import { UnitInputComponent } from './unit-input.component';
import { OperatorComponent } from './operator.component';
import { ResultComponent } from './result.component';
import { HistoryComponent } from './history.component';
import { AuthComponent } from './auth.component';

import { UnitFormatPipe } from './unit-format.pipe';
import { ComparisonSymbolPipe } from './comparison-symbol.pipe';
import { GlobalErrorInterceptor } from './interceptors/global-error.interceptor';
import { AuthInterceptor } from './interceptors/auth.interceptor';

@NgModule({
  declarations: [
    App,
    TypeSelectorComponent,
    ActionTabsComponent,
    UnitInputComponent,
    OperatorComponent,
    ResultComponent,
    HistoryComponent,
    AuthComponent,
    UnitFormatPipe,
    ComparisonSymbolPipe
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: GlobalErrorInterceptor, multi: true }
  ],
  bootstrap: [App]
})
export class AppModule { }
