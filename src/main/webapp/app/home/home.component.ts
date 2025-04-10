import { Component, OnDestroy, OnInit, inject, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Sanphamhot, CART_ITEMS } from 'app/data/sanphamhot/sanphamhot';
import { Suachoai, CART_ITEMS_SUA } from 'app/data/suachoai/suachoai';
import { PRODUCT_GROUPS, ProductGroup } from 'app/data/home-group';
import { PRODUCTS, Product } from 'app/data/product';
// Import Swiper
import Swiper from 'swiper';
import { Navigation, Pagination } from 'swiper/modules';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  standalone: true,
  imports: [SharedModule, RouterModule],
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

  ngOnInit(): void {
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
      slidesPerView: 3, // 3 sản phẩm mỗi hàng
      grid: {
        rows: 2, // 2 hàng
        fill: 'row', // điền theo hàng
      },
      spaceBetween: 15, // khoảng cách giữa các sản phẩm
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
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
  //  ----------------------------------------------------------------
}
