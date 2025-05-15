import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private baseUrl = 'http://localhost:8080/api/cart';

  constructor(private http: HttpClient) {}

  getCart(): Observable<any> {
    return this.http.get(this.baseUrl);
  }

  addToCart(cartItem: any): Observable<any> {
    return this.http.post(this.baseUrl, cartItem);
  }

  updateCartItem(id: number, cartItem: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, cartItem);
  }
}
