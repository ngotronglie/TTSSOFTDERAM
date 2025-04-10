export interface NewsChild {
  id: number;
  title: string;
  url: string;
}

export interface NewsCategory {
  id: number;
  title: string;
  image: string;
  url: string;
  children: NewsChild[];
}

export const TIN_TUC_NEW: NewsCategory[] = [
  {
    id: 1,
    title: 'Khuyến mãi hấp dẫn',
    image: 'assets/icons/hot.svg',
    url: '/tin-tuc/khuyen-mai',
    children: [],
  },
  {
    id: 2,
    title: 'Sản phẩm mới',
    image: 'assets/icons/new.svg',
    url: '/tin-tuc/san-pham-moi',
    children: [],
  },
  {
    id: 3,
    title: 'Quà Tặng Hấp Dẫn',
    image: 'assets/icons/gift.svg',
    url: '/tin-tuc/qua-tang',
    children: [
      { id: 1, title: 'Tặng sữa miễn phí', url: '/tin-tuc/qua-tang/sua-mien-phi' },
      { id: 2, title: 'Combo ưu đãi', url: '/tin-tuc/qua-tang/combo-uu-dai' },
      { id: 3, title: 'Quà sinh nhật', url: '/tin-tuc/qua-tang/sinh-nhat' },
    ],
  },
  {
    id: 4,
    title: 'Tích điểm thành viên',
    image: 'assets/icons/star.svg',
    url: '/tin-tuc/tich-diem',
    children: [],
  },
  {
    id: 5,
    title: 'Hỗ trợ khách hàng',
    image: 'assets/icons/help.svg',
    url: '/tin-tuc/ho-tro',
    children: [
      { id: 1, title: 'Gọi hỗ trợ 24/7', url: '/tin-tuc/ho-tro/ho-tro-247' },
      { id: 2, title: 'Gửi yêu cầu', url: '/tin-tuc/ho-tro/gui-yeu-cau' },
    ],
  },
  {
    id: 6,
    title: 'Chính sách',
    image: 'assets/icons/policy.svg',
    url: '/tin-tuc/chinh-sach',
    children: [
      { id: 1, title: 'Đổi trả', url: '/tin-tuc/chinh-sach/doi-tra' },
      { id: 2, title: 'Bảo hành', url: '/tin-tuc/chinh-sach/bao-hanh' },
      { id: 3, title: 'Giao hàng', url: '/tin-tuc/chinh-sach/giao-hang' },
      { id: 4, title: 'Bảo mật', url: '/tin-tuc/chinh-sach/bao-mat' },
      { id: 5, title: 'Điều khoản', url: '/tin-tuc/chinh-sach/dieu-khoan' },
    ],
  },
];
