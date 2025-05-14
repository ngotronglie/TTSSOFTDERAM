import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule],
})
export default class CartComponent implements OnInit {
  cartItems: any[] = [];
  user: any = null;

  order = {
    fullName: '',
    email: '',
    address: '',
    phoneNumber: '',
    paymentMethod: '1',
  };

  isLoggedIn(): boolean {
    return !!localStorage.getItem('user');
  }
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadCart();
    this.loadUser(); // ✅ Gọi hàm load user
  }

  loadCart(): void {
    if (this.isLoggedIn()) {
      const user = JSON.parse(localStorage.getItem('user') || '{}');
      const idUser = user.id_user ? user.id_user : 0;
      this.http.get(`http://localhost:8080/api/cart/user/${idUser}`).subscribe({
        next: (response: any) => {
          this.cartItems = response.data;
          console.log(this.cartItems);
        },
        error: (error: any) => {
          console.error('Lỗi khi lấy giỏ hàng:', error);
        },
      });
    } else {
      const storedCart = localStorage.getItem('cart');
      if (storedCart) {
        this.cartItems = JSON.parse(storedCart);
      }
    }
  }

  updateCart(): void {
    localStorage.setItem('cart', JSON.stringify(this.cartItems));
  }

  updateCartHttp(items: any) {
    this.http.put('http://localhost:8080/api/cart/update', items).subscribe();
  }

  increaseQuantity(item: any): void {
    if (this.isLoggedIn()) {
      item.quantity++;
      const items = {
        userId: item.userId,
        productId: item.productId,
        quantity: item.quantity,
      };
      this.updateCartHttp(items);
    } else {
      item.quantity++;
      this.updateCart();
    }
  }

  decreaseQuantity(item: any): void {
    if (this.isLoggedIn()) {
      if (item.quantity > 1) {
        item.quantity--;
        const items = {
          userId: item.userId,
          productId: item.productId,
          quantity: item.quantity,
        };
        this.updateCartHttp(items);
      }
    } else {
      if (item.quantity > 1) {
        item.quantity--;
        this.updateCart();
      }
    }
  }

  removeItem(index: number, item: any): void {
    if (this.isLoggedIn()) {
      console.log('user id: ' + item.userId);
      console.log('product id : ' + item.productId);
      this.http.delete(`http://localhost:8080/api/cart/remove/${item.userId}/${item.productId}`).subscribe();
      this.cartItems.splice(index, 1);
      console.log('dax chay qua day');
    } else {
      this.cartItems.splice(index, 1);
      this.updateCart();
    }
  }

  get totalPrice(): number {
    return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  // xoas cart
  clearCart(): void {
    localStorage.removeItem('cart');
    this.cartItems = [];
  }

  // submit
  onSubmit(): void {
    if (this.cartItems.length === 0) {
      alert('Giỏ hàng trống. Vui lòng thêm sản phẩm!');
      return;
    }

    const { fullName, email, address, phoneNumber } = this.order;

    let ThongBaoCart = [];

    if (!fullName) {
      ThongBaoCart.push('vui long nhap ho ten...');
    }

    if (!email) {
      ThongBaoCart.push('vui long nhap email');
    }

    if (!address) {
      ThongBaoCart.push('vui long nhap dia chi');
    }

    if (!phoneNumber) {
      ThongBaoCart.push('vui long nhap so dien thoai');
    }

    if (ThongBaoCart.length > 0) {
      alert(ThongBaoCart.join('\n'));
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
