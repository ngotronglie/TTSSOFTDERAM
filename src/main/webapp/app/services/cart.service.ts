// Import các thư viện cần thiết
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root', // Cho phép service này được sử dụng toàn cục trong app
})
export class CartService {
  // Tạo BehaviorSubject để lưu danh sách sản phẩm trong giỏ hàng
  private cartItems = new BehaviorSubject<any[]>([]);
  // Tạo BehaviorSubject để lưu tổng số lượng sản phẩm trong giỏ
  private numberOfItems = new BehaviorSubject<number>(0);

  constructor() {
    // Khi service được khởi tạo, load giỏ hàng từ localStorage
    this.loadCart();
  }

  // Hàm load dữ liệu giỏ hàng từ localStorage
  private loadCart(): void {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]'); // Lấy dữ liệu từ localStorage
    this.cartItems.next(cart); // Cập nhật cartItems
    this.updateNumberOfItems(); // Cập nhật tổng số lượng
  }

  // Cập nhật tổng số lượng sản phẩm trong giỏ
  private updateNumberOfItems(): void {
    const items = this.cartItems.getValue(); // Lấy danh sách hiện tại
    const total = items.reduce((sum, item) => sum + item.quantity, 0); // Tính tổng quantity
    this.numberOfItems.next(total); // Cập nhật numberOfItems
  }

  // Trả về Observable của tổng số lượng sản phẩm (dùng để binding ở component)
  getNumberOfItems(): Observable<number> {
    return this.numberOfItems.asObservable();
  }

  // Thêm sản phẩm vào giỏ hàng
  addToCart(product: any): void {
    const currentItems = this.cartItems.getValue(); // Lấy danh sách hiện tại
    const existingItem = currentItems.find(item => item.productId === product.id_product); // Kiểm tra sản phẩm đã có chưa

    if (existingItem) {
      existingItem.quantity += 1; // Nếu có rồi thì tăng số lượng
    } else {
      // Nếu chưa có thì thêm mới vào giỏ
      currentItems.push({
        productId: product.id_product,
        name: product.name,
        price: product.price,
        image: product.image,
        quantity: 1,
      });
    }

    this.cartItems.next(currentItems); // Cập nhật danh sách cartItems
    localStorage.setItem('cart', JSON.stringify(currentItems)); // Lưu lại vào localStorage
    this.updateNumberOfItems(); // Cập nhật tổng số lượng
  }

  // Xóa sản phẩm khỏi giỏ hàng
  removeFromCart(productId: number): void {
    const currentItems = this.cartItems.getValue(); // Lấy danh sách hiện tại
    const updatedItems = currentItems.filter(item => item.productId !== productId); // Lọc bỏ sản phẩm muốn xóa
    this.cartItems.next(updatedItems); // Cập nhật cartItems
    localStorage.setItem('cart', JSON.stringify(updatedItems)); // Lưu lại vào localStorage
    this.updateNumberOfItems(); // Cập nhật tổng số lượng
  }

  // Cập nhật lại số lượng sản phẩm
  updateQuantity(productId: number, quantity: number): void {
    const currentItems = this.cartItems.getValue(); // Lấy danh sách hiện tại
    const item = currentItems.find(item => item.productId === productId); // Tìm sản phẩm cần cập nhật
    if (item) {
      item.quantity = quantity; // Gán lại số lượng
      this.cartItems.next(currentItems); // Cập nhật cartItems
      localStorage.setItem('cart', JSON.stringify(currentItems)); // Lưu lại vào localStorage
      this.updateNumberOfItems(); // Cập nhật tổng số lượng
    }
  }

  // Trả về Observable của danh sách sản phẩm trong giỏ (component sẽ subscribe)
  getCartItems(): Observable<any[]> {
    return this.cartItems.asObservable();
  }

  // Xóa toàn bộ giỏ hàng
  clearCart(): void {
    this.cartItems.next([]); // Reset lại cartItems
    localStorage.removeItem('cart'); // Xóa khỏi localStorage
    this.updateNumberOfItems(); // Cập nhật tổng số lượng về 0
  }
}
