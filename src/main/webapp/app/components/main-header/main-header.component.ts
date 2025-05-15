// Import các decorator và interface cần thiết từ Angular core
import { Component, OnInit, OnDestroy } from '@angular/core';
// Import Router để điều hướng
import { Router, RouterLink } from '@angular/router';
// Import CartService để lấy số lượng sản phẩm trong giỏ hàng
import { CartService } from '../../services/cart.service';
// Import Subscription để quản lý việc đăng ký observable
import { Subscription } from 'rxjs';

// Khai báo metadata cho component
@Component({
  selector: 'jhi-main-header', // Tên selector để sử dụng component trong template
  templateUrl: './main-header.component.html', // Đường dẫn đến file HTML template
  styleUrls: ['./main-header.component.scss'], // Đường dẫn đến file CSS
  imports: [RouterLink], // Import RouterLink cho template dùng routerLink
})
export class MainHeaderComponent implements OnInit, OnDestroy {
  // Biến lưu thông tin người dùng
  userloading: any = {};
  // Biến đếm số lượng sản phẩm trong giỏ hàng
  numberOfItems = 0;
  // Subscription để hủy đăng ký observable khi component bị hủy
  private subscription: Subscription;

  // Inject các service cần thiết qua constructor
  constructor(
    private router: Router, // Service điều hướng
    private cartService: CartService, // Service quản lý giỏ hàng
  ) {
    // Đăng ký để nhận số lượng sản phẩm trong giỏ hàng từ CartService
    this.subscription = this.cartService.getNumberOfItems().subscribe(count => {
      this.numberOfItems = count; // Gán giá trị nhận được cho biến hiển thị
    });
  }

  // Hàm khởi tạo sau khi component được dựng
  ngOnInit(): void {
    this.loadIdUser(); // Gọi hàm load thông tin người dùng từ localStorage
    this.checkLocalStorage(); // Kiểm tra và lấy số lượng sản phẩm từ localStorage

    // Đăng ký lại để đảm bảo cập nhật số lượng giỏ hàng khi component được khởi tạo
    this.cartService.getNumberOfItems().subscribe(count => {
      this.numberOfItems = count;
    });
  }

  // Hàm được gọi khi component bị hủy (thoát khỏi màn hình)
  ngOnDestroy(): void {
    // Hủy đăng ký observable để tránh rò rỉ bộ nhớ
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  //  login
  isLoggedIn(): boolean {
    return !!localStorage.getItem('user');
  }

  // Hàm để load thông tin người dùng từ localStorage
  loadIdUser(): void {
    const userJson = localStorage.getItem('user'); // Lấy dữ liệu user dạng JSON
    if (userJson) {
      this.userloading = JSON.parse(userJson); // Chuyển đổi từ JSON sang object
      console.log('User loaded from localStorage:', this.userloading); // In thông tin user
    } else {
      console.warn('Không tìm thấy user trong localStorage.'); // Thông báo nếu không có user
    }
  }

  // Kiểm tra số lượng sản phẩm có trong localStorage (giỏ hàng offline)
  checkLocalStorage() {
    const cartData = localStorage.getItem('cart'); // Lấy dữ liệu cart từ localStorage
    if (cartData) {
      const cartArray = JSON.parse(cartData); // Chuyển từ JSON sang mảng
      this.numberOfItems = Array.isArray(cartArray) ? cartArray.length : 0; // Đếm số lượng sản phẩm
    } else {
      this.numberOfItems = 0; // Nếu không có thì đặt về 0
    }

    console.log('Số lượng sản phẩm trong giỏ hàng:', this.numberOfItems); // In ra console
  }

  // Hàm xử lý đăng xuất
  logout(): void {
    // Xóa thông tin người dùng khỏi localStorage
    localStorage.removeItem('user');

    // Thông báo logout thành công
    alert('Logout thành công!');

    // Cập nhật lại số lượng sản phẩm sau khi logout
    this.checkLocalStorage();

    // Điều hướng về trang chủ (hoặc có thể là trang login tùy mục đích)
    this.router.navigate(['/']);
  }
}
