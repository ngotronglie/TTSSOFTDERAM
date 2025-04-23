import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class RegisterService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  // getProfile() {
  //   return this.http.get(`${this.baseUrl}/profile`, { withCredentials: true });
  // }

  // updateProfile(data: { name: string; email: string }) {
  //   return this.http.put(`${this.baseUrl}/profile`, data, { withCredentials: true });
  // }
  // Hàm đăng ký người dùng
  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, user);
  }
}
