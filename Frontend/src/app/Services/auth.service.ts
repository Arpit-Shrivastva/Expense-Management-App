import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { RegisterRequest } from '../Dto/register-request.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
    private readonly BASE_URL = 'http://localhost:8080/auth';
  private readonly TOKEN_KEY = 'token';
  private readonly USERNAME_KEY = 'username';
  private readonly ROLE_KEY = 'role';
  private readonly USER_ID_KEY = 'userId'; 

  constructor(private http: HttpClient) {}

  register(request: RegisterRequest): Observable<string> {
    return this.http.post(`${this.BASE_URL}/register`, request, { responseType: 'text' });
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.BASE_URL}/login`, credentials).pipe(
      tap((res) => {
        localStorage.setItem(this.TOKEN_KEY, res.token);
        localStorage.setItem(this.USERNAME_KEY, res.username);
        localStorage.setItem(this.ROLE_KEY, res.role);
        localStorage.setItem(this.USER_ID_KEY, res.id); // ✅ Store userId
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USERNAME_KEY);
    localStorage.removeItem(this.ROLE_KEY);
    localStorage.removeItem(this.USER_ID_KEY);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem(this.TOKEN_KEY);
  }

  getUsername(): string | null {
    return localStorage.getItem(this.USERNAME_KEY);
  }

  getRole(): string | null {
    return localStorage.getItem(this.ROLE_KEY);
  }

  getUserId(): number | null {
    const id = localStorage.getItem(this.USER_ID_KEY);
    return id ? +id : null;
  }

}
