
import { Component } from '@angular/core';


@Component({
  selector: 'app-header',
  standalone: true, // Cho phép component hoạt động độc lập mà không cần khai báo trong @NgModule
  // template: '<h1>HEllo world template</h1>',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  // styles: ['h1 { color: red; }']

})
export class HeaderComponent {

}
