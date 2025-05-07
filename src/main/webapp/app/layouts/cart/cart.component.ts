import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
})
export default class CartComponent implements OnInit {
  cartItems: any[] = [];

  order = {
    fullName: '',
    email: '',
    address: '',
    phoneNumber: '',
    paymentMethod: 'cash',
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    const storedCart = localStorage.getItem('cart');
    if (storedCart) {
      this.cartItems = JSON.parse(storedCart);
    }
  }

  updateCart(): void {
    localStorage.setItem('cart', JSON.stringify(this.cartItems));
  }

  increaseQuantity(item: any): void {
    item.quantity++;
    this.updateCart();
  }

  decreaseQuantity(item: any): void {
    if (item.quantity > 1) {
      item.quantity--;
      this.updateCart();
    }
  }

  removeItem(index: number): void {
    this.cartItems.splice(index, 1);
    this.updateCart();
  }

  get totalPrice(): number {
    return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  clearCart(): void {
    localStorage.removeItem('cart');
    this.cartItems = [];
  }

  onSubmit(): void {
    if (this.cartItems.length === 0) {
      alert('Giỏ hàng trống. Vui lòng thêm sản phẩm!');
      return;
    }

    const { fullName, email, address, phoneNumber } = this.order;
    if (!fullName || !email || !address || !phoneNumber) {
      alert('Vui lòng điền đầy đủ thông tin đơn hàng!');
      return;
    }

    // Tạo đơn hàng gửi lên backend
    const payload = {
      customerName: this.order.fullName,
      email: this.order.email,
      address: this.order.address,
      phoneNumber: this.order.phoneNumber,
      paymentMethod: this.order.paymentMethod,
      orderDetails: this.cartItems.map(item => ({
        productId: item.productId,
        productName: item.name,
        quantity: item.quantity,
        price: item.price,
      })),
    };

    this.http.post('/api/orders', payload).subscribe({
      next: () => {
        alert('Đặt hàng thành công!');
        this.clearCart();
        this.order = {
          fullName: '',
          email: '',
          address: '',
          phoneNumber: '',
          paymentMethod: 'cash',
        };
      },
      error: err => {
        console.error('Lỗi khi đặt hàng:', err);
        alert('Đặt hàng thất bại!');
      },
    });
  }
}
