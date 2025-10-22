import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResetpasswordService {

  private readonly BASE_URL = 'http://localhost:8080/password';
  private readonly TOKEN_KEY = 'token';


  constructor(private http: HttpClient) { }


  forgotPassword(email: string): Observable<string> {
    return this.http.post(`${this.BASE_URL}/forgot`, { email }, { responseType: 'text' });
  }

  resetPassword(token: string, newPassword: string): Observable<string> {
    return this.http.post(`${this.BASE_URL}/reset`, { token, newPassword }, { responseType: 'text' });
  }
}
