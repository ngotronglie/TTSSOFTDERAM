import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegisterService } from 'app/layouts/register/register.service';
import { CommonModule } from '@angular/common'; // Import RegisterService

@Component({
  selector: 'jhi-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, RouterLink], // ✅ phải có cái này!
})
export class RegisterComponent implements OnInit, AfterViewInit {
  @ViewChild('firstnameInput') firstnameInput!: ElementRef;
  registerForm: FormGroup;
  authenticationError = false;

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService, // Inject RegisterService
    private router: Router,
  ) {
    this.registerForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.firstnameInput?.nativeElement.focus();
  }

  // Hàm xử lý đăng ký
  register(): void {
    if (this.registerForm.valid) {
      const user = this.registerForm.value;

      // Gọi API đăng ký thông qua service
      this.registerService.register(user).subscribe({
        next: () => {
          alert('Đăng ký thành công!');
          this.router.navigate(['/login']); // Chuyển hướng sang trang đăng nhập
        },
        error: () => {
          this.authenticationError = true; // Hiển thị lỗi đăng ký
        },
      });
    }
  }
}
