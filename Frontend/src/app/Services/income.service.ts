import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IncomeRequest } from '../Dto/IncomeRequest';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class IncomeService {

  private apiUrl = 'http://localhost:8080/income';

  constructor(private http: HttpClient) {}

  // Add income
  addIncome(request: IncomeRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}`, request, { responseType: 'text' });
  }

  // Get total income for a specific month
  getTotalIncomeForMonth(month: string): Observable<number> {
    const params = new HttpParams().set('month', month); 
    return this.http.get<number>(`${this.apiUrl}`, { params });
  }
}
