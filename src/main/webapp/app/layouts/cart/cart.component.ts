import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CART_ITEMS, CartItem } from 'app/data/cart/cart';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  standalone: true,
  imports: [CommonModule],
})
export default class CartComponent {
  cartItems: CartItem[] = CART_ITEMS; // Sử dụng dữ liệu từ cart.data.ts

  get totalPrice(): number {
    return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  increaseQuantity(item: CartItem): void {
    item.quantity++;
  }

  decreaseQuantity(item: CartItem): void {
    if (item.quantity > 1) {
      item.quantity--;
    }
  }

  removeItem(index: number): void {
    this.cartItems.splice(index, 1);
  }
}
