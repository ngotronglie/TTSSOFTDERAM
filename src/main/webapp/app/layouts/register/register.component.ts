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
      role_id: ['1', Validators.required],
    });
  }

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.firstnameInput?.nativeElement.focus();
  }

  register(): void {
    if (this.registerForm.valid) {
      const formValues = this.registerForm.value;

      // In dữ liệu của form ra console
      console.log('Form Values:', formValues);
      // Tạo FormData
      const formData = new FormData();
      formData.append('firstname', formValues.firstname);
      formData.append('lastname', formValues.lastname);
      formData.append('email', formValues.email);
      formData.append('phone', formValues.phone);
      formData.append('password', formValues.password);
      formData.append('role_id', formValues.role_id); // Mặc định là "1"
      console.log(formData);
      // Gọi service để gửi FormData
      this.registerService.register(formData).subscribe({
        next: () => {
          alert('Đăng ký thành công!');
          this.router.navigate(['/login']);
        },
        error: () => {
          this.authenticationError = true;
        },
      });
    }
  }
}
