import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

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

  constructor(private http: HttpClient) {}

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

  cancelOrder(): void {
    // Implement cancel order logic here
    alert('Hủy đơn hàng thành công!');
  }

  getStatusClass(status: string): string {
    switch (status.toLowerCase()) {
      case 'pending':
        return 'text-warning';
      case 'completed':
        return 'text-success';
      case 'cancelled':
        return 'text-danger';
      default:
        return 'text-secondary';
    }
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
}
