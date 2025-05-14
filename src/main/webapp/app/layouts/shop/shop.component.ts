import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'jhi-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss'],
  imports: [CommonModule, RouterLink, FormsModule],
})
export default class ShopComponent implements OnInit {
  products: any[] = [];
  filteredProducts: any[] = [];
  paginatedProducts: any[] = [];
  currentPage: number = 1;
  productsPerPage: number = 8;

  // Filter states
  priceFilters: { [key: string]: boolean } = {
    under100k: false,
    from100kTo200k: false,
    from200kTo500k: false,
    from500kTo1M: false,
    from1MTo2M: false,
    above2M: false,
  };

  typeFilters: { [key: string]: boolean } = {
    type1: false,
    type2: false,
    type3: false,
  };

  sortOption: string = 'default';

  constructor(
    private http: HttpClient,
    private cartService: CartService,
  ) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.http.get<any>('http://localhost:8080/api/products').subscribe({
      next: response => {
        if (response.status === 'success') {
          this.products = response.data;
          this.applyFilters();
        } else {
          console.error('Failed to load products.');
        }
      },
      error: err => {
        console.error('Error loading products', err);
      },
    });
  }

  applyFilters(): void {
    // Apply price filters
    this.filteredProducts = this.products.filter(product => {
      const price = product.price;

      // Check if any price filter is active
      const hasActivePriceFilter = Object.values(this.priceFilters).some(value => value);

      if (hasActivePriceFilter) {
        if (this.priceFilters.under100k && price < 100000) return true;
        if (this.priceFilters.from100kTo200k && price >= 100000 && price <= 200000) return true;
        if (this.priceFilters.from200kTo500k && price > 200000 && price <= 500000) return true;
        if (this.priceFilters.from500kTo1M && price > 500000 && price <= 1000000) return true;
        if (this.priceFilters.from1MTo2M && price > 1000000 && price <= 2000000) return true;
        if (this.priceFilters.above2M && price > 2000000) return true;
        return false;
      }

      // Apply type filters
      const hasActiveTypeFilter = Object.values(this.typeFilters).some(value => value);
      if (hasActiveTypeFilter) {
        if (this.typeFilters.type1 && product.type === 'type1') return true;
        if (this.typeFilters.type2 && product.type === 'type2') return true;
        if (this.typeFilters.type3 && product.type === 'type3') return true;
        return false;
      }

      return true;
    });

    // Apply sorting
    this.sortProducts();

    // Update pagination
    this.updatePagination();
  }

  sortProducts(): void {
    switch (this.sortOption) {
      case 'az':
        this.filteredProducts.sort((a, b) => a.name.localeCompare(b.name));
        break;
      case 'za':
        this.filteredProducts.sort((a, b) => b.name.localeCompare(a.name));
        break;
      case 'price_asc':
        this.filteredProducts.sort((a, b) => a.price - b.price);
        break;
      case 'price_desc':
        this.filteredProducts.sort((a, b) => b.price - a.price);
        break;
      case 'new':
        this.filteredProducts.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
        break;
      default:
        // Default sorting - no change
        break;
    }
  }

  updatePagination(): void {
    const startIndex = (this.currentPage - 1) * this.productsPerPage;
    const endIndex = startIndex + this.productsPerPage;
    this.paginatedProducts = this.filteredProducts.slice(startIndex, endIndex);
  }

  setPage(page: number): void {
    if (page < 1 || page > this.getTotalPages()) return;
    this.currentPage = page;
    this.updatePagination();
  }

  getTotalPages(): number {
    return Math.ceil(this.filteredProducts.length / this.productsPerPage);
  }

  onFilterChange(): void {
    this.currentPage = 1; // Reset to first page when filters change
    this.applyFilters();
  }

  onSortChange(): void {
    this.applyFilters();
  }

  addToCart(product: any): void {
    this.cartService.addToCart(product);
    alert('Thêm vào giỏ hàng thành công');
  }
}
