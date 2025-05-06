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
  account = signal<Account | null>(null);
  cartItems: Sanphamhot[] = CART_ITEMS;
  cartItems_SUA: Suachoai[] = CART_ITEMS_SUA;
  productGroups: ProductGroup[] = [];
  products: Product[] = PRODUCTS;

  private readonly destroy$ = new Subject<void>();
  private readonly accountService = inject(AccountService);
  private readonly router = inject(Router);

  banners: any[] = []; // Array to hold banners
  private apiUrl = 'http://localhost:8080/api/banners'; // Replace with your actual API endpoint

  constructor(private http: HttpClient) {} // Inject HttpClient into the component
  ngOnInit(): void {
    this.loadBanners(); // Call the function to load banners on component init
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

  //  ----------------------------------------------------------------
}
