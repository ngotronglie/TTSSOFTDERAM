import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TopHeaderComponent } from './top-header.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    TopHeaderComponent, // Import trực tiếp vì nó là standalone component
  ],
  exports: [TopHeaderComponent],
})
export class HeaderModule {}
