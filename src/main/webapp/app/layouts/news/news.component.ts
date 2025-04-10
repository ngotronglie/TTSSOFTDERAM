import { Component } from '@angular/core';
import { TIN_TUC, news } from 'app/data/tintuc';
import { CommonModule } from '@angular/common';
import { TIN_TUC_NEW, NewsCategory } from 'app/data/new-category';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'jhi-news',
  standalone: true,
  imports: [CommonModule, RouterModule], //
  templateUrl: './news.component.html',
  styleUrl: './news.component.scss',
})
export class NewsComponent {
  newItems: news[] = TIN_TUC;
  newsCategory: NewsCategory[] = [];

  constructor() {
    this.newsCategory = TIN_TUC_NEW; // GÁN DỮ LIỆU Ở ĐÂY
  }
}
