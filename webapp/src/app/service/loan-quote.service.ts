import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoanQuoteService {

  constructor(private httpClient: HttpClient) { }

  postLoan(body: any): Observable<any> {
    return this.httpClient.put<any>(environment.API_ENDPOINT + '/api/loan/calculate', body);
  }
}
