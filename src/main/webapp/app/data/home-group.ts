export interface ProductItem {
  id: number;
  name: string;
  image: string;
  price?: string; // Giá hiển thị
  priceOld?: string; // Giá cũ (nếu có)
  brand?: string; // Thương hiệu
  tags?: string[]; // ['sale', 'hot', 'new']
  unit?: string; // Đơn vị (hộp, lon, túi, ...), nếu cần
  rating?: number; // Điểm đánh giá (1-5 sao)
  isOutOfStock?: boolean; // Hết hàng hay không
}

export interface ProductGroup {
  id: number;
  title: string;
  description?: string;
  bannerImage: string; // Banner ở bên trái
  brandLogos: string[]; // Logo thương hiệu nằm dưới banner
  products: ProductItem[]; // Danh sách sản phẩm
}

export const PRODUCT_GROUPS: ProductGroup[] = [
  {
    id: 1,
    title: 'Sữa Cho Bé 0-12 Tháng',
    description: 'Chăm sóc bé toàn diện từ tháng đầu tiên với những dòng sữa chất lượng cao.',
    bannerImage: 'assets/images/banners/banner-baby.png',
    brandLogos: ['assets/logos/nan.png', 'assets/logos/meiji.png', 'assets/logos/friso.png', 'assets/logos/similac.png'],
    products: [
      {
        id: 101,
        name: 'Sữa Nan Optipro 1 HMO 800g',
        image: 'assets/images/products/nan1.png',
        price: '450.000₫',
        priceOld: '490.000₫',
        brand: 'Nestlé',
        tags: ['sale'],
        unit: 'hộp',
        rating: 4.5,
        isOutOfStock: false,
      },
      {
        id: 102,
        name: 'Sữa Meiji số 0 - 800g',
        image: 'assets/images/products/meiji0.png',
        price: '520.000₫',
        brand: 'Meiji',
        tags: ['hot'],
        unit: 'hộp',
        rating: 5,
        isOutOfStock: false,
      },
      {
        id: 103,
        name: 'Sữa Friso Gold 1 - 850g',
        image: 'assets/images/products/friso1.png',
        price: '480.000₫',
        priceOld: '510.000₫',
        brand: 'Friso',
        tags: ['sale', 'hot'],
        unit: 'lon',
        rating: 4,
        isOutOfStock: true,
      },
      {
        id: 104,
        name: 'Similac Eye-Q 1 900g',
        image: 'assets/images/products/similac1.png',
        price: '495.000₫',
        brand: 'Abbott',
        tags: ['new'],
        unit: 'lon',
        rating: 4.2,
        isOutOfStock: false,
      },
    ],
  },
  {
    id: 2,
    title: 'Sữa Cho Người Cao Tuổi',
    description: 'Tăng cường sức khỏe xương và trí não cho người lớn tuổi.',
    bannerImage: 'assets/images/banners/banner-elderly.png',
    brandLogos: ['assets/logos/anlene.png', 'assets/logos/ensure.png', 'assets/logos/varna.png'],
    products: [
      {
        id: 201,
        name: 'Anlene Gold MovePro 800g',
        image: 'assets/images/products/anlene.png',
        price: '375.000₫',
        brand: 'Anlene',
        tags: ['new'],
        unit: 'hộp',
        rating: 4.8,
        isOutOfStock: false,
      },
      {
        id: 202,
        name: 'Ensure Gold Vigor 850g',
        image: 'assets/images/products/ensure.png',
        price: '610.000₫',
        priceOld: '650.000₫',
        brand: 'Abbott',
        tags: ['sale'],
        unit: 'lon',
        rating: 5,
        isOutOfStock: false,
      },
      {
        id: 203,
        name: 'Sữa Varna Complete 900g',
        image: 'assets/images/products/varna.png',
        price: '540.000₫',
        brand: 'Vinamilk',
        unit: 'lon',
        rating: 4.6,
        isOutOfStock: false,
      },
    ],
  },
];
