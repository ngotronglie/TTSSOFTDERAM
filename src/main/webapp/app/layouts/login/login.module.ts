// src/main/webapp/app/layouts/login/login.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from './login.service';
import { RouterModule } from '@angular/router'; // Để sử dụng các routerLink trong template

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule, // Để sử dụng các routerLink trong template
  ],
  providers: [LoginService], // Đảm bảo LoginService được cung cấp trong module này
})
export class LoginModule {}
