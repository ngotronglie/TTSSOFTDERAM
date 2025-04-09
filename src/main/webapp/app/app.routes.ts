import { Routes } from '@angular/router';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { errorRoute } from './layouts/error/error.route';
const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./home/home.component'),
    title: 'home.title',
  },
  {
    path: '',
    loadComponent: () => import('./layouts/header/header.component').then(m => m.HeaderComponent),
    outlet: 'navbar',
  },
  {
    path: 'admin',
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./admin/admin.routes'),
  },
  {
    path: 'account',
    loadChildren: () => import('./account/account.route'),
  },
  {
    path: 'login',
    loadComponent: () => import('./layouts/login/login.component'),
    title: 'login.title',
  },
  {
    path: 'register',
    loadComponent: () => import('./layouts/register/register.component'),
    title: 'register.title',
  },
  {
    path: 'forgot-password',
    loadComponent: () => import('./layouts/forgot-password/forgot-password.component'),
    title: 'forgot-password.title',
  },
  {
    path: 'cart',
    loadComponent: () => import('./layouts/cart/cart.component'),
    title: 'cart.title',
  },
  {
    path: 'shop',
    loadComponent: () => import('./layouts/shop/shop.component'),
    title: 'shop.title',
  },
  {
    path: '',
    loadChildren: () => import(`./entities/entity.routes`),
  },
];

export default routes;
