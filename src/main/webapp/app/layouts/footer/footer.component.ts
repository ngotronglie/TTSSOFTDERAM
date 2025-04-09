import { Component } from '@angular/core';
import { ContactFixComponent } from 'app/components/contact-fix/contact-fix.component';
@Component({
  selector: 'jhi-footer',
  imports: [ContactFixComponent],
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
})
export default class FooterComponent {}
