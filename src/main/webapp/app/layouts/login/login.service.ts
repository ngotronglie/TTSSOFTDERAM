import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private baseUrl = 'http://localhost:8080/api/users'; // Cập nhật baseUrl của bạn

  constructor(private http: HttpClient) {}

  // Phương thức đăng nhập (login)
  login(credentials: { email: string; password: string }): Observable<any> {
    // Tạo đối tượng FormData để gửi dữ liệu dưới dạng multipart/form-data
    const formData = new FormData();
    formData.append('email', credentials.email);
    formData.append('password', credentials.password);

    // In ra để kiểm tra dữ liệu
    console.log('FormData:', formData);

    // Tiến hành gửi yêu cầu POST với kiểu multipart/form-data
    return this.http.post(`${this.baseUrl}/login`, formData, {
      headers: new HttpHeaders(), // Trình duyệt sẽ tự động thêm Content-Type cho multipart/form-data
    });
  }

  // Phương thức đăng xuất (logout)
  logout(): void {
    localStorage.removeItem('auth_token');
    sessionStorage.removeItem('auth_token'); // Nếu bạn sử dụng sessionStorage
    window.location.href = '/login'; // Hoặc sử dụng Router để chuyển hướng
  }
}
