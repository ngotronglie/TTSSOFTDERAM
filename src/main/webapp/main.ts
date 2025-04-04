import { register } from 'swiper/element/bundle';
register();

import('./bootstrap').catch((err: unknown) => console.error(err));
