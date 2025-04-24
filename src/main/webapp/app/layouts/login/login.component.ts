// src/main/webapp/app/layouts/login/login.component.ts
import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from './login.service'; // Import LoginService
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router'; // Để sử dụng các routerLink trong template

@Component({
  selector: 'jhi-login',
  standalone: true, // Đảm bảo LoginComponent là standalone
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, RouterModule], // Các module cần thiết cho standalone component
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('firstnameInput') firstnameInput!: ElementRef;
  loginForm: FormGroup;
  authenticationError = false;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService, // Inject LoginService
    private router: Router,
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.firstnameInput?.nativeElement.focus();
  }

  login(): void {
    // In ra trạng thái của form để kiểm tra
    console.log('Login Form: ', this.loginForm);
    console.log('Is form valid?', this.loginForm.valid);

    if (this.loginForm.valid) {
      const formValues = this.loginForm.value;

      // In ra các giá trị form để kiểm tra xem chúng có đúng không
      console.log('Form values: ', formValues);

      // Gọi service login
      this.loginService.login(formValues).subscribe({
        next: () => {
          this.authenticationError = false;
          console.log('Login successful');
          alert('đăng nhập thành công');
          this.router.navigate(['/']); // Navigate to home page after successful login
        },
        error: err => {
          this.authenticationError = true;
          alert('sai email hoăặc mật khẩu');
          console.error('Login error: ', err); // In lỗi nếu có
        },
      });
    } else {
      console.log('Form is invalid, not proceeding with login.');
    }
  }
}
