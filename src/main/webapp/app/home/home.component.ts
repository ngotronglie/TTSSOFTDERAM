import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Subject } from 'rxjs';
import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { CART_ITEMS, Sanphamhot } from 'app/data/sanphamhot/sanphamhot';
import { CART_ITEMS_SUA, Suachoai } from 'app/data/suachoai/suachoai';
import { PRODUCT_GROUPS, ProductGroup } from 'app/data/home-group';
import { Product, PRODUCTS } from 'app/data/product';
// Import Swiper
import Swiper from 'swiper';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  standalone: true,
  imports: [SharedModule, RouterModule, CommonModule],
})
export default class HomeComponent implements OnInit, OnDestroy {
  userId: number | null = null;
  account = signal<Account | null>(null);
  cartItems: Sanphamhot[] = CART_ITEMS;
  cartItems_SUA: Suachoai[] = CART_ITEMS_SUA;
  productGroups: ProductGroup[] = [];
  products: Product[] = PRODUCTS;

  private readonly destroy$ = new Subject<void>();
  private readonly accountService = inject(AccountService);
  private readonly router = inject(Router);

  banners: any[] = []; // Array to hold banners
  productss: any[] = [];
  user: any = {};
  private apiUrl = 'http://localhost:8080/api/banners'; // Replace with your actual API endpoint
  private apiProductUrl = 'http://localhost:8080/api/products'; // api sản phẩm
  private apiGetUserId = 'http://localhost:8080/api/users/get-user';

  constructor(private http: HttpClient) {} // Inject HttpClient into the component
  ngOnInit(): void {
    this.loadBanners(); // Call the function to load banners on component init
    this.loadProduct(); // goi san pham
    this.loadIdUser(); // goi session
    new Swiper('.product-slider', {
      slidesPerView: 2, // Hiển thị 2 sản phẩm cùng lúc
      spaceBetween: 12, // Khoảng cách giữa các sản phẩm
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
    });
    new Swiper('.for-slider', {
      slidesPerView: 7, // Hiển thị 7 sản phẩm cùng lúc
      spaceBetween: 8, // Khoảng cách giữa các sản phẩm
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      breakpoints: {
        0: {
          slidesPerView: 2,
        },
        576: {
          slidesPerView: 2,
        },
        768: {
          slidesPerView: 3,
        },
        992: {
          slidesPerView: 7,
        },
      },
    });

    new Swiper('.product-2-3', {
      slidesPerView: 3, // mặc định 3 sản phẩm mỗi hàng
      grid: {
        rows: 2, // luôn 2 hàng
        fill: 'row', // điền theo hàng
      },
      spaceBetween: 15,
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      breakpoints: {
        0: {
          slidesPerView: 1, // màn nhỏ chỉ hiện 1 sản phẩm mỗi hàng => tổng 2 sản phẩm trên 2 hàng
          grid: {
            rows: 2,
          },
        },
        576: {
          slidesPerView: 2,
          grid: {
            rows: 2,
          },
        },
        768: {
          slidesPerView: 3,
          grid: {
            rows: 2,
          },
        },
      },
    });

    this.productGroups = PRODUCT_GROUPS;
    this.products = PRODUCTS;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadBanners(): void {
    this.http.get<any>(this.apiUrl).subscribe(
      response => {
        if (response.status === 'success') {
          this.banners = response.data; // ✅ Lấy đúng mảng data
        } else {
          console.error('Error: API returned non-success status');
        }
      },
      error => {
        console.error('Error fetching banners:', error); // Xử lý lỗi
      },
    );
  }

  loadIdUser(): void {
    this.http.get<any>(this.apiGetUserId).subscribe(
      response => {
        if (response.status === 'success') {
          this.user = response.data; // ✅ Lấy đúng mảng data
        } else {
          console.error('Error: API returned non-success status');
        }
      },
      error => {
        console.error('Error fetching banners:', error); // Xử lý lỗi
      },
    );
  }
  addToCart(product: any, userId: number | null) {
    if (userId) {
      // Người dùng đã đăng nhập -> Gọi API để thêm vào giỏ hàng server
      const cartItem = {
        userId: userId,
        productId: product.id_product,
        quantity: 1, // Hoặc bạn cho chọn số lượng
      };

      // Gọi API thêm vào giỏ hàng
      this.http.post('/api/cart/add', cartItem).subscribe(
        response => {
          console.log('Thêm vào giỏ hàng server thành công', response);
        },
        error => {
          console.error('Lỗi thêm vào giỏ hàng server', error);
        },
      );
    } else {
      // Khách vãng lai -> Lưu LocalStorage
      let cart = JSON.parse(localStorage.getItem('cart') || '[]');

      // Kiểm tra nếu sản phẩm đã tồn tại thì tăng quantity
      const existingItem = cart.find((item: any) => item.productId === product.id_product);
      if (existingItem) {
        existingItem.quantity += 1;
      } else {
        cart.push({
          productId: product.id_product,
          name: product.name,
          price: product.price,
          image: product.image,
          quantity: 1,
        });
      }

      localStorage.setItem('cart', JSON.stringify(cart));
      console.log('Thêm vào giỏ hàng local thành công');
    }
  }

  loadProduct(): void {
    this.http.get<any>(this.apiProductUrl).subscribe(
      response => {
        if (response.status === 'success') {
          this.productss = response.data; // ✅ Lấy đúng mảng data
        } else {
          console.error('Error: API returned non-success status');
        }
      },
      error => {
        console.error('Error fetching banners:', error); // Xử lý lỗi
      },
    );
  }

  //  ----------------------------------------------------------------
}
