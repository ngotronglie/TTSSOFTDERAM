import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterLink],
})
export default class CartComponent implements OnInit {
  cartItems: any[] = []; // Mảng chứa các sản phẩm trong giỏ hàng
  ngOnInit(): void {
    this.loadCart(); // Khi component được khởi tạo, tải dữ liệu giỏ hàng từ localStorage
  }
  // Lấy giỏ hàng từ localStorage
  loadCart(): void {
    const storedCart = localStorage.getItem('cart');
    if (storedCart) {
      this.cartItems = JSON.parse(storedCart);
    }
  }
  // Tính tổng tiền của giỏ hàng
  get totalPrice(): number {
    return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  // Tăng số lượng sản phẩm
  increaseQuantity(item: any): void {
    item.quantity++;
    this.updateCart(); // Cập nhật lại giỏ hàng trong localStorage
  }

  // Giảm số lượng sản phẩm
  decreaseQuantity(item: any): void {
    if (item.quantity > 1) {
      item.quantity--;
      this.updateCart(); // Cập nhật lại giỏ hàng trong localStorage
    }
  }

  // Cập nhật giỏ hàng trong localStorage
  updateCart(): void {
    localStorage.setItem('cart', JSON.stringify(this.cartItems));
  }

  // Xóa sản phẩm khỏi giỏ hàng
  removeItem(index: number): void {
    this.cartItems.splice(index, 1);
    this.updateCart(); // Cập nhật lại giỏ hàng trong localStorage
  }

  // Thêm sản phẩm vào giỏ hàng
  addToCart(product: any): void {
    // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
    const existingItem = this.cartItems.find(item => item.productId === product.productId);

    if (existingItem) {
      // Nếu sản phẩm đã tồn tại, tăng số lượng
      existingItem.quantity++;
    } else {
      // Nếu chưa có, thêm sản phẩm mới vào giỏ hàng
      this.cartItems.push({
        productId: product.productId,
        name: product.name,
        price: product.price,
        image: product.image,
        quantity: 1,
      });
    }
    this.updateCart(); // Cập nhật lại giỏ hàng trong localStorage
  }
}
