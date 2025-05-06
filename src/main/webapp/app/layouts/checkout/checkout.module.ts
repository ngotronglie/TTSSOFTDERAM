import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Để sử dụng ngModel trong form
import { CheckoutComponent } from './checkout.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule, // Để sử dụng ngModel trong form
  ],
  declarations: [CheckoutComponent],
  exports: [CheckoutComponent], // Export để sử dụng ngoài module này nếu cần
})
export class CheckoutModule {}
