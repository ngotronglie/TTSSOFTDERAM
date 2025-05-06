import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router'; // Import FormsModule for ngModel

@Component({
  selector: 'jhi-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink], // Import CommonModule and FormsModule to use common Angular features and forms
})
export class CheckoutComponent implements OnInit {
  // Dữ liệu người dùng nhập vào
  order = {
    fullName: '',
    email: '',
    address: '',
    phoneNumber: '',
    paymentMethod: 'cash',
  };

  // Giỏ hàng từ localStorage
  cartItems: any[] = [];

  constructor() {}

  ngOnInit(): void {
    this.loadCart();
  }

  // Lấy giỏ hàng từ localStorage
  loadCart(): void {
    const storedCart = localStorage.getItem('cart');
    if (storedCart) {
      this.cartItems = JSON.parse(storedCart);
      console.log(this.cartItems);
    }
  }

  // Tính tổng tiền
  get totalPrice(): number {
    return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  // Xử lý form gửi
  onSubmit(): void {
    if (this.cartItems.length === 0) {
      alert('Giỏ hàng của bạn trống. Vui lòng thêm sản phẩm!');
      return;
    }

    if (!this.order.fullName || !this.order.email || !this.order.address || !this.order.phoneNumber) {
      alert('Vui lòng điền đầy đủ thông tin!');
      return;
    }

    // Logic xử lý thanh toán ở đây (ví dụ: gọi API thanh toán)
    alert('Thanh toán thành công!');
    this.clearCart();
  }

  // Xóa giỏ hàng sau khi thanh toán thành công
  clearCart(): void {
    localStorage.removeItem('cart');
    this.cartItems = [];
  }
}
