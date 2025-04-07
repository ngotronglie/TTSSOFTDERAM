import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { HeaderComponent } from './app/header/header.component';
import { register } from 'swiper/element';
register();
bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));

bootstrapApplication(HeaderComponent, appConfig)
.catch((err) => console.error(err));

console.log('Hello World!');
