import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'jhi-main-header',
  templateUrl: './main-header.component.html',
  styleUrls: ['./main-header.component.scss'],
  imports: [RouterLink],
})
export class MainHeaderComponent implements OnInit {
  userloading: any = {};

  constructor(private router: Router) {}

  // Được gọi khi component khởi tạo
  ngOnInit(): void {
    this.loadIdUser();
  }

  // Hàm để load thông tin người dùng từ localStorage
  loadIdUser(): void {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      this.userloading = JSON.parse(userJson);
      console.log('User loaded from localStorage:', this.userloading);
      // alert(JSON.stringify(this.userloading));
    } else {
      console.warn('Không tìm thấy user trong localStorage.');
    }
  }

  // Hàm logout
  logout(): void {
    // Xóa thông tin người dùng và token
    localStorage.removeItem('user');
    sessionStorage.removeItem('auth_token');

    alert('Logout thành công!');

    // Điều hướng về trang chủ hoặc trang đăng nhập
    window.location.reload();
    this.router.navigate(['/']); // Bạn có thể thay đổi /login nếu muốn chuyển tới trang đăng nhập
  }
}
