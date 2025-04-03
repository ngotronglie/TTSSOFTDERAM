import { Component } from '@angular/core';
import { MainHeaderComponent } from 'app/components/main-header/main-header.component';
import { NavMenuComponent } from 'app/components/nav-menu/nav-menu.component';
import { TopHeaderComponent } from 'app/components/top-header/top-header.component';

@Component({
  selector: 'jhi-header',
  standalone: true,
  imports: [MainHeaderComponent, NavMenuComponent, TopHeaderComponent], // ✅ Import đúng
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {}
