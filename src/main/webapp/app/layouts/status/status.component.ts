import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface OrderDetail {
  orderDetailId: number;
  productId: number;
  productName: string;
  productImage: string;
  quantity: number;
  price: number;
}

interface Order {
  orderId: number;
  code: string;
  totalPrice: number;
  status: string;
  fullName: string;
  phone: string;
  email: string;
  address: string;
  paymentMethod: string;
  createdAt: string;
  orderDetails: OrderDetail[];
}

interface ApiResponse {
  status: string;
  message: string;
  timestamp: string;
  data: Order | Order[];
  errors: any;
}

interface UpdateOrderRequest {
  status_orders: string;
}

@Component({
  selector: 'jhi-status',
  standalone: true,
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class StatusComponent implements OnInit {
  user: any = null;
  OrderDetails: Order[] = [];
  searchedOrder: Order | null = null;
  noOrdersMessage: string = '';
  orderCode: string = '';
  isLoading: boolean = false;
  searchError: string = '';

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  ngOnInit(): void {
    const userData = localStorage.getItem('user');
    if (userData) {
      try {
        this.user = JSON.parse(userData);
        if (this.user && this.user.id_user) {
          this.loadOrdersForUser(this.user.id_user);
        }
      } catch (e) {
        this.user = null;
      }
    }
  }

  loadOrdersForUser(UserId: string): void {
    this.http.get<ApiResponse>(`http://localhost:8080/api/orders/user/${UserId}`).subscribe({
      next: response => {
        if (response.status === 'success') {
          if (response.data && Array.isArray(response.data) && response.data.length > 0) {
            this.OrderDetails = response.data;
            this.noOrdersMessage = '';
          } else {
            this.noOrdersMessage = 'Bạn chưa có đơn hàng nào!';
          }
        } else {
          this.noOrdersMessage = 'Không thể tải thông tin đơn hàng.';
        }
      },
      error: error => {
        console.error('Error loading orders:', error);
        this.noOrdersMessage = 'Lỗi khi tải đơn hàng. Vui lòng thử lại sau.';
      },
    });
  }

  searchOrder(): void {
    if (!this.orderCode) {
      this.searchError = 'Vui lòng nhập mã đơn hàng!';
      return;
    }

    this.isLoading = true;
    this.searchError = '';
    this.searchedOrder = null;

    this.http.get<ApiResponse>(`http://localhost:8080/api/orders/code/${this.orderCode}`).subscribe({
      next: response => {
        if (response.status === 'success' && response.data && !Array.isArray(response.data)) {
          this.searchedOrder = response.data;
        } else {
          this.searchError = 'Không tìm thấy đơn hàng với mã này!';
        }
      },
      error: error => {
        console.error('Error searching order:', error);
        this.searchError = 'Lỗi khi tìm kiếm đơn hàng. Vui lòng thử lại sau.';
      },
      complete: () => {
        this.isLoading = false;
      },
    });
  }

  cancelOrderByCode(code: string): void {
    if (!code) {
      alert('Mã đơn hàng không hợp lệ!');
      return;
    }

    const updateData: UpdateOrderRequest = {
      status_orders: 'tra hang',
    };

    this.http.put<ApiResponse>(`http://localhost:8080/api/orders/code/${code}`, updateData).subscribe({
      next: response => {
        if (response.status === 'success') {
          alert('Cập nhật trạng thái đơn hàng thành công!');
          // Reload the searched order to show updated status
          this.searchOrder();
        } else {
          alert('Cập nhật trạng thái đơn hàng thất bại!');
        }
      },
      error: error => {
        console.error('Error updating order:', error);
        alert('Lỗi khi cập nhật trạng thái đơn hàng. Vui lòng thử lại sau.');
      },
    });
  }

  cancelOrder(orderId: number): void {
    const updateData: UpdateOrderRequest = {
      status_orders: 'tra hang',
    };

    this.http.put<ApiResponse>(`http://localhost:8080/api/orders/user/${this.user.id_user}`, updateData).subscribe({
      next: response => {
        if (response.status === 'success') {
          alert('Cập nhật trạng thái đơn hàng thành công!');
          // Reload orders after update
          this.loadOrdersForUser(this.user.id_user);
          // Clear search if it was showing
          this.searchedOrder = null;
          this.orderCode = '';
        } else {
          alert('Cập nhật trạng thái đơn hàng thất bại!');
        }
      },
      error: error => {
        console.error('Error updating order:', error);
        alert('Lỗi khi cập nhật trạng thái đơn hàng. Vui lòng thử lại sau.');
      },
    });
  }

  getStatusClass(status: string): string {
    const statusClasses: { [key: string]: string } = {
      pending: 'text-info',
      processing: 'text-warning',
      completed: 'text-success',
      cancelled: 'text-danger',
    };

    return statusClasses[status.toLowerCase()] || 'text-secondary';
  }

  getPaymentMethodText(method: string): string {
    switch (method) {
      case '1':
        return 'Thanh toán khi nhận hàng';
      case '2':
        return 'Chuyển khoản ngân hàng';
      default:
        return 'Không xác định';
    }
  }

  buyAgain(order: Order): void {
    // Add all products from the order to cart
    order.orderDetails.forEach(detail => {
      // Fetch complete product information
      this.http.get<any>(`http://localhost:8080/api/products/${detail.productId}`).subscribe({
        next: productResponse => {
          if (productResponse.status === 'success' && productResponse.data) {
            const product = productResponse.data;
            const cartItem = {
              productId: detail.productId,
              quantity: detail.quantity,
              productName: product.name,
              productImage: product.image,
              price: product.price,
            };

            // Add to cart using localStorage
            const cart = JSON.parse(localStorage.getItem('cart') || '[]');
            const existingItem = cart.find((item: any) => item.productId === detail.productId);

            if (existingItem) {
              existingItem.quantity += detail.quantity;
            } else {
              cart.push(cartItem);
            }

            localStorage.setItem('cart', JSON.stringify(cart));
          }
        },
        error: error => {
          console.error('Error fetching product details:', error);
          alert('Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng. Vui lòng thử lại sau.');
        },
        complete: () => {
          // Navigate to cart page after all products are processed
          this.router.navigate(['/cart']);
        },
      });
    });
  }
}
