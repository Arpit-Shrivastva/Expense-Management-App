import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExpenseRequest } from '../Dto/expense-request';
import { Observable } from 'rxjs';
import { ExpenseResponse } from '../Dto/expense-response';
import { MonthlyExpenseSummaryDTO } from '../Dto/MonthlyExpenseSummaryDTO';
import { YearlyExpenseSummaryDTO } from '../Dto/YearlyExpenseSummaryDTO';

@Injectable({
  providedIn: 'root'
})
export class ExpensesService {
  
  private readonly BASE_URL = 'http://localhost:8080/expenses';

  constructor(private http: HttpClient) { }

  addExpense(request: ExpenseRequest): Observable<ExpenseResponse> {
    return this.http.post<ExpenseResponse>(this.BASE_URL, request);
  }

  getExpenses(): Observable<ExpenseResponse[]> {
    return this.http.get<ExpenseResponse[]>(this.BASE_URL);
  }

  updateExpense(id: number, request: ExpenseRequest): Observable<ExpenseResponse> {
    return this.http.put<ExpenseResponse>(`${this.BASE_URL}/${id}`, request);
  }

  deleteExpense(id: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE_URL}/${id}`);
  }

  getExpensesByDateRange(start: string, end: string): Observable<ExpenseResponse[]> {
    const params = new HttpParams().set('start', start).set('end', end);
    return this.http.get<ExpenseResponse[]>(`${this.BASE_URL}/date-range`, { params });
  }

  getExpensesByCategory(category: string): Observable<ExpenseResponse[]> {
    const params = new HttpParams().set('category', category);
    return this.http.get<ExpenseResponse[]>(`${this.BASE_URL}/by-category`, { params });
  }

  /** ✅ Monthly summary for entire year */
  getMonthlySummary(year: number): Observable<MonthlyExpenseSummaryDTO[]> {
    const params = new HttpParams().set('year', year.toString());
    return this.http.get<MonthlyExpenseSummaryDTO[]>(`${this.BASE_URL}/monthly`, { params });
  }

  /** ✅ Monthly summary for a specific month (e.g. '2025-07') */
  getMonthlySummaryForMonth(month: string): Observable<MonthlyExpenseSummaryDTO> {
    const params = new HttpParams().set('month', month);
    return this.http.get<MonthlyExpenseSummaryDTO>(`${this.BASE_URL}/summary`, { params });
  }

  getYearlySummary(year: number): Observable<YearlyExpenseSummaryDTO> {
    const params = new HttpParams().set('year', year.toString());
    return this.http.get<YearlyExpenseSummaryDTO>(`${this.BASE_URL}/yearly`, { params });
  }
}