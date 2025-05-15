import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-main-header',
  templateUrl: './main-header.component.html',
  styleUrls: ['./main-header.component.scss'],
  imports: [RouterLink],
})
export class MainHeaderComponent implements OnInit, OnDestroy {
  userloading: any = {};
  numberOfItems = 0;
  private subscription: Subscription;

  constructor(
    private router: Router,
    private cartService: CartService,
  ) {
    this.subscription = this.cartService.getNumberOfItems().subscribe(count => {
      this.numberOfItems = count;
    });
  }

  ngOnInit(): void {
    this.loadIdUser();
    this.checkLocalStorage();
    // Initial load of cart items
    this.cartService.getNumberOfItems().subscribe(count => {
      this.numberOfItems = count;
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  // Hàm để load thông tin người dùng từ localStorage
  loadIdUser(): void {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      this.userloading = JSON.parse(userJson);
      console.log('User loaded from localStorage:', this.userloading);
    } else {
      console.warn('Không tìm thấy user trong localStorage.');
    }
  }

  // Kiểm tra số lượng phần tử trong localStorage
  checkLocalStorage() {
    const cartData = localStorage.getItem('cart');
    if (cartData) {
      const cartArray = JSON.parse(cartData);
      this.numberOfItems = Array.isArray(cartArray) ? cartArray.length : 0;
    } else {
      this.numberOfItems = 0;
    }

    console.log('Số lượng sản phẩm trong giỏ hàng:', this.numberOfItems);
  }

  // Hàm logout
  logout(): void {
    // Xóa thông tin người dùng và token khỏi localStorage và sessionStorage
    localStorage.removeItem('user');
    sessionStorage.removeItem('auth_token');

    alert('Logout thành công!');

    // Cập nhật lại số lượng phần tử trong localStorage sau khi logout
    this.checkLocalStorage();

    // Điều hướng về trang chủ sau khi logout (hoặc trang đăng nhập)
    this.router.navigate(['/']); // Điều hướng về trang chủ
  }
}
