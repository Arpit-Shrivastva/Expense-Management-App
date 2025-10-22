import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BudgetResponse } from '../Dto/BudgetResponse';
import { Observable } from 'rxjs';
import { BudgetRequest } from '../Dto/BudgetRequest';
import { BudgetComparisonResponse } from '../Dto/BudgetComparisonResponse';

@Injectable({
  providedIn: 'root'
})
export class BudgetService {

  private readonly BASE_URL = 'http://localhost:8080/budgets';

  constructor(private http: HttpClient) {}

  /** POST: Set a new budget for a category */
  setBudget(request: BudgetRequest): Observable<BudgetResponse> {
    return this.http.post<BudgetResponse>(this.BASE_URL, request);
  }

  /** GET: Get budgets for a specific month (YYYY-MM) */
  getBudgets(month: string): Observable<BudgetResponse[]> {
    const params = new HttpParams().set('month', month);
    return this.http.get<BudgetResponse[]>(this.BASE_URL, { params });
  }

  /** GET: Compare budget vs spending for a month */
  compareBudget(month: string): Observable<BudgetComparisonResponse[]> {
    const params = new HttpParams().set('month', month);
    return this.http.get<BudgetComparisonResponse[]>(`${this.BASE_URL}/comparison`, { params });
  }
}
