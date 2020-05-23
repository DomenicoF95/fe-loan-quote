import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoanQuoteComponent } from './router/loan-quote/loan-quote.component';
import { LoginComponent } from './router/login/login.component';
import { HomeComponent } from './router/home/home.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoanQuoteService } from './service/loan-quote.service';
import {HttpClient, HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    LoanQuoteComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [LoanQuoteService, HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
