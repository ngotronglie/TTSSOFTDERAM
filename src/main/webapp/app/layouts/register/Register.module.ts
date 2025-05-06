import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // Đảm bảo đã import ReactiveFormsModule
import { RegisterComponent } from './register.component';
import { RegisterService } from './register.service';
import { RouterModule } from '@angular/router'; // Import RouterModule nếu cần điều hướng

@NgModule({
  imports: [
    CommonModule,
    FormsModule, // Bạn có thể để FormsModule nếu cần
    ReactiveFormsModule, // Thêm ReactiveFormsModule để sử dụng formGroup trong component
    RouterModule, // Thêm RouterModule để sử dụng các route nếu cần,
    RegisterComponent,
  ],
  providers: [RegisterService], // Cung cấp RegisterService cho module
  exports: [RegisterComponent], // Export RegisterComponent để có thể sử dụng ở các module khác
})
export class RegisterModule {}
