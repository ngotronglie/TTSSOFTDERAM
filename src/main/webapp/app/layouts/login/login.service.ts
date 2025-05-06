import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router'; // Import Router

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  // Phương thức đăng nhập (login)
  login(credentials: { email: string; password: string }): Observable<any> {
    const formData = new FormData();
    formData.append('email', credentials.email);
    formData.append('password', credentials.password);

    console.log('FormData:', formData);

    return this.http
      .post(`${this.baseUrl}/login`, formData, {
        headers: new HttpHeaders(),
        withCredentials: true,
      })
      .pipe(
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
    localStorage.removeItem('user'); // ✅ Xóa user nếu có
    sessionStorage.removeItem('auth_token');
    window.location.href = '/login';
  }
}
