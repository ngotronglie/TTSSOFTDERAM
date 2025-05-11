import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  // Phương thức đăng nhập (login)
  login(credentials: { email: string; password: string }): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post(`http://localhost:8080/auth/login`, credentials, { headers }).pipe(
      tap((res: any) => {
        if (res.status === 'success' && res.data) {
          // ✅ Lưu user vào localStorage
          localStorage.setItem('user', JSON.stringify(res.data));
        }
      }),
    );
  }

  // Phương thức đăng xuất (logout)
  logout(): void {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user');
    sessionStorage.removeItem('auth_token');
    window.location.href = '/login';
  }
}
