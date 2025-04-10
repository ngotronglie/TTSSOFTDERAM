export interface news {
  id: number;
  title: string;
  image: string;
  comment: number;
  admin_add: string;
  status: boolean;
}

export const TIN_TUC: news[] = [
  {
    id: 1,
    title: 'Top 10 Sữa Bột Cho Trẻ 2 Tuổi Phát Triển Toàn Diện',
    image: 'content/images/tintuc/image-tintuc-1.webp',
    comment: 6,
    admin_add: 'Khang MKT',
    status: true,
  },
  {
    id: 2,
    title: 'Bí quyết chọn sữa cho trẻ biếng ăn',
    image: 'content/images/tintuc/image-tintuc-2.webp',
    comment: 3,
    admin_add: 'Khang MKT',
    status: true,
  },
  {
    id: 3,
    title: 'Sữa tăng chiều cao nào tốt cho bé 3 tuổi?',
    image: 'content/images/tintuc/image-tintuc-3.webp',
    comment: 8,
    admin_add: 'Khang MKT',
    status: true,
  },
  {
    id: 4,
    title: 'Lợi ích của DHA trong sữa cho trẻ em',
    image: 'content/images/tintuc/image-tintuc-4.webp',
    comment: 5,
    admin_add: 'Khang MKT',
    status: true,
  },
  {
    id: 5,
    title: 'Sữa công thức hữu cơ: Xu hướng mới cho bé yêu',
    image: 'content/images/tintuc/image-tintuc-5.webp',
    comment: 2,
    admin_add: 'Khang MKT',
    status: true,
  },
  {
    id: 6,
    title: 'Top 5 thương hiệu sữa được bác sĩ khuyên dùng',
    image: 'content/images/tintuc/image-tintuc-6.webp',
    comment: 9,
    admin_add: 'Khang MKT',
    status: true,
  },
];
