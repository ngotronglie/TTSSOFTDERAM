import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import Swiper from 'swiper';

import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { CART_ITEMS, Sanphamhot } from 'app/data/sanphamhot/sanphamhot';
import { CART_ITEMS_SUA, Suachoai } from 'app/data/suachoai/suachoai';
import { PRODUCT_GROUPS, ProductGroup } from 'app/data/home-group';
import { Product, PRODUCTS } from 'app/data/product';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  standalone: true,
  imports: [SharedModule, RouterModule, CommonModule],
})
export default class HomeComponent implements OnInit, OnDestroy {
  // Inject service
  private readonly accountService = inject(AccountService);
  private readonly router = inject(Router);
  private readonly http = inject(HttpClient);
  private cartService: CartService;

  // State
  account = signal<Account | null>(null);
  userId: number | null = null;
  userloading: any = {};

  banners: any[] = [];
  productGroups: ProductGroup[] = PRODUCT_GROUPS;
  products: Product[] = PRODUCTS;
  cartItems: Sanphamhot[] = CART_ITEMS;
  cartItems_SUA: Suachoai[] = CART_ITEMS_SUA;
  productss: any[] = [];
  productAll: any[] = [];

  isLoggedIn(): boolean {
    return !!localStorage.getItem('user');
  }

  private readonly destroy$ = new Subject<void>();
  // API Endpoints
  private readonly apiUrl = 'http://localhost:8080/api/banners';
  private readonly apiProductUrl = 'http://localhost:8080/api/products';
  private readonly apiGetUserId = 'http://localhost:8080/api/users/get-username';
  private readonly apiLogout = 'http://localhost:8080/api/users/logout';
  private readonly apiShowProduct = 'http://localhost:8080/api/category-structure';

  constructor(cartService: CartService) {
    this.cartService = cartService;
  }

  ngOnInit(): void {
    this.initSwipers();
    this.loadUser();
    this.loadBanners();
    this.loadProducts();
    this.loadAllProducts();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private loadUser(): void {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      this.userloading = JSON.parse(userJson);
      console.log('User loaded:', this.userloading);
    } else {
      console.warn('No user found in localStorage.');
    }
  }

  private loadBanners(): void {
    this.http.get<any>(this.apiUrl).subscribe({
      next: response => {
        if (response.status === 'success') {
          this.banners = response.data;
        } else {
          console.error('Failed to load banners.');
        }
      },
      error: error => {
        console.error('Error loading banners:', error);
      },
    });
  }

  private loadProducts(): void {
    this.http.get<any>(this.apiProductUrl).subscribe({
      next: response => {
        if (response.status === 'success') {
          this.productss = response.data;
        } else {
          console.error('Failed to load products.');
        }
      },
      error: error => {
        console.error('Error loading products:', error);
      },
    });
  }

  private loadAllProducts(): void {
    this.http.get<any>(this.apiShowProduct).subscribe({
      next: response => {
        if (response.status === 'success') {
          this.productAll = Object.keys(response.data).map(key => {
            const item = response.data[key];
            return {
              categoryName: key,
              imageCategory: item.image_category,
              branches: item.image_branch,
              products: Object.values(item.product),
              children: Object.values(item.category_parent_child),
            };
          });
          console.log('All Products:', this.productAll);
        } else {
          console.error('Failed to load all products.');
        }
      },
      error: error => {
        console.error('Error loading all products:', error);
      },
    });
  }

  private initSwipers(): void {
    // Product slider
    new Swiper('.product-slider', {
      slidesPerView: 2,
      spaceBetween: 12,
      navigation: { nextEl: '.swiper-button-next', prevEl: '.swiper-button-prev' },
      pagination: { el: '.swiper-pagination', clickable: true },
    });

    // For slider
    new Swiper('.for-slider', {
      slidesPerView: 7,
      spaceBetween: 8,
      navigation: { nextEl: '.swiper-button-next', prevEl: '.swiper-button-prev' },
      pagination: { el: '.swiper-pagination', clickable: true },
      breakpoints: {
        0: { slidesPerView: 2 },
        576: { slidesPerView: 2 },
        768: { slidesPerView: 3 },
        992: { slidesPerView: 7 },
      },
    });

    // Product 2x3 grid
    new Swiper('.product-2-3', {
      slidesPerView: 3,
      grid: { rows: 2, fill: 'row' },
      spaceBetween: 15,
      navigation: { nextEl: '.swiper-button-next', prevEl: '.swiper-button-prev' },
      pagination: { el: '.swiper-pagination', clickable: true },
      breakpoints: {
        0: { slidesPerView: 1, grid: { rows: 2 } },
        576: { slidesPerView: 2, grid: { rows: 2 } },
        768: { slidesPerView: 3, grid: { rows: 2 } },
      },
    });
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.http.post(this.apiLogout, {}).subscribe({
      next: () => {
        localStorage.removeItem('user');
        this.router.navigate(['/login']);
      },
      error: error => {
        console.error('Logout failed:', error);
      },
    });
  }

  addToCart(product: any): void {
    if (this.isLoggedIn()) {
      const userJson = localStorage.getItem('user');
      if (userJson) {
        const user = JSON.parse(userJson);
        console.log(user);
        console.log(product);
        const cartData = {
          userId: user.id_user,
          productId: product.id,
          quantity: 1,
        };
        console.log(cartData);
        this.http.post('http://localhost:8080/api/cart/add', cartData).subscribe({
          next: response => {
            console.log('Product added to cart:', response);
            alert('Thêm vào giỏ hàng thành công');
          },
          error: error => {
            console.error('Error adding to cart:', error);
            alert('Có lỗi xảy ra khi thêm vào giỏ hàng');
          },
        });
      }
    } else {
      this.cartService.addToCart(product);
      alert('Thêm vào giỏ hàng thành công');
    }
  }
}
