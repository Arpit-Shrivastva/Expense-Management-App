import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { UserUpdateRequest } from '../Dto/UserUpdateRequest';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly BASE_URL = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }

  updateUser(id: number, user: UserUpdateRequest): Observable<any> {
    return this.http.put(`${this.BASE_URL}/${id}`, user);
  }

  getUserById(id: number): Observable<UserUpdateRequest> {
    return this.http.get<UserUpdateRequest>(`${this.BASE_URL}/${id}`);
  }
}
