import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoanQuoteComponent} from "./router/loan-quote/loan-quote.component";
import {LoginComponent} from "./router/login/login.component";
import {HomeComponent} from "./router/home/home.component";


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'loanQuote', component: LoanQuoteComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
