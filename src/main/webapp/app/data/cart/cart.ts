export interface CartItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
  location: string;
  image: string;
}

export const CART_ITEMS: CartItem[] = [
  {
    id: 1,
    name: 'Rontamil Nutri-pro Kid 400g',
    price: 300000,
    quantity: 1,
    location: 'BẮC GIANG',
    image: 'assets/images/cart/cart-product-1',
  },
  {
    id: 2,
    name: 'Sữa bột Enfa A+ 360 900g',
    price: 550000,
    quantity: 2,
    location: 'HÀ NỘI',
    image: 'assets/images/cart/cart-product-1',
  },
];
