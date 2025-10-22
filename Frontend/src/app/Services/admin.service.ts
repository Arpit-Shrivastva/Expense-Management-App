import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from '../Dto/auth-response.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {


  private readonly BASE_URL = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) { }

  /** GET all users */
  getAllUsers(): Observable<AuthResponse[]> {
    return this.http.get<AuthResponse[]>(`${this.BASE_URL}/users`);
  }

  /** DELETE user by username */
  deleteUser(username: string): Observable<string> {
    return this.http.delete(`${this.BASE_URL}/user/${username}`, { responseType: 'text' });
  }
}
