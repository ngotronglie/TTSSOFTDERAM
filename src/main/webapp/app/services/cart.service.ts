import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartItems = new BehaviorSubject<any[]>([]);
  private numberOfItems = new BehaviorSubject<number>(0);

  constructor() {
    // Load cart from localStorage on service initialization
    this.loadCart();
  }

  private loadCart(): void {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    this.cartItems.next(cart);
    this.updateNumberOfItems();
  }

  private updateNumberOfItems(): void {
    const items = this.cartItems.getValue();
    const total = items.reduce((sum, item) => sum + item.quantity, 0);
    this.numberOfItems.next(total);
  }

  getNumberOfItems(): Observable<number> {
    return this.numberOfItems.asObservable();
  }

  addToCart(product: any): void {
    const currentItems = this.cartItems.getValue();
    const existingItem = currentItems.find(item => item.productId === product.id_product);

    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      currentItems.push({
        productId: product.id_product,
        name: product.name,
        price: product.price,
        image: product.image,
        quantity: 1,
      });
    }

    this.cartItems.next(currentItems);
    localStorage.setItem('cart', JSON.stringify(currentItems));
    this.updateNumberOfItems();
  }

  removeFromCart(productId: number): void {
    const currentItems = this.cartItems.getValue();
    const updatedItems = currentItems.filter(item => item.productId !== productId);
    this.cartItems.next(updatedItems);
    localStorage.setItem('cart', JSON.stringify(updatedItems));
    this.updateNumberOfItems();
  }

  updateQuantity(productId: number, quantity: number): void {
    const currentItems = this.cartItems.getValue();
    const item = currentItems.find(item => item.productId === productId);
    if (item) {
      item.quantity = quantity;
      this.cartItems.next(currentItems);
      localStorage.setItem('cart', JSON.stringify(currentItems));
      this.updateNumberOfItems();
    }
  }

  getCartItems(): Observable<any[]> {
    return this.cartItems.asObservable();
  }

  clearCart(): void {
    this.cartItems.next([]);
    localStorage.removeItem('cart');
    this.updateNumberOfItems();
  }
}
