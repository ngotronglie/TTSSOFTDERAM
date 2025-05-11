import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule], // ❌ Xóa RouterLink nếu không dùng trong template
})
export default class CartComponent implements OnInit {
  cartItems: any[] = [];
  user: any = null;

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
    this.loadUser(); // ✅ Gọi hàm load user
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

    const user = JSON.parse(localStorage.getItem('user') || '{}');

    const idUser = user.id_user ? user.id_user : 0;
    const payload = {
      userId: idUser,
      fullName: this.order.fullName,
      email: this.order.email,
      address: this.order.address,
      phoneNumber: this.order.phoneNumber,
      paymentMethod: this.order.paymentMethod,
      totalPrice: this.totalPrice,
      orderDetails: this.cartItems.map(item => ({
        productId: item.productId,
        productName: item.name,
        quantity: item.quantity,
        price: item.price,
      })),
    };

    console.log(payload);

    this.http.post('/api/orders/user-add', payload).subscribe({
      next: () => {
        alert('Đặt hàng thành công!');
        this.clearCart();
        this.order = {
          fullName: '',
          email: '',
          address: '',
          phoneNumber: '',
          paymentMethod: '',
        };
      },
      error: err => {
        console.error('Lỗi khi đặt hàng:', err);
        alert('Đặt hàng thất bại!');
      },
    });
  }

  loadUser(): void {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);

      if (this.user.firstname && this.user.lastname && this.user.email) {
        this.order.fullName = `${this.user.firstname} ${this.user.lastname}`;
        this.order.email = this.user.email;
      }
    }
  }
}
