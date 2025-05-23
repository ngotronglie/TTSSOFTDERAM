import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'jhi-top-header',
  imports: [CommonModule, FormsModule], // Import FormsModule vào đây
  templateUrl: './top-header.component.html',
  styleUrls: ['./top-header.component.scss'],
  standalone: true,
})
export class TopHeaderComponent {
  selectedCity = '';

  cities = [
    { value: 'hanoi', name: 'Hà Nội' },
    { value: 'hochiminh', name: 'TP. Hồ Chí Minh' },
    { value: 'haiphong', name: 'Hải Phòng' },
    { value: 'danang', name: 'Đà Nẵng' },
    { value: 'cantho', name: 'Cần Thơ' },
    { value: 'angiang', name: 'An Giang' },
    { value: 'bariavungtau', name: 'Bà Rịa - Vũng Tàu' },
    { value: 'bacgiang', name: 'Bắc Giang' },
    { value: 'backan', name: 'Bắc Kạn' },
    { value: 'baclieu', name: 'Bạc Liêu' },
    { value: 'bacninh', name: 'Bắc Ninh' },
    { value: 'bentre', name: 'Bến Tre' },
    { value: 'binhduong', name: 'Bình Dương' },
    { value: 'binhdinh', name: 'Bình Định' },
    { value: 'binhphuoc', name: 'Bình Phước' },
    { value: 'binhthuan', name: 'Bình Thuận' },
    { value: 'camau', name: 'Cà Mau' },
    { value: 'caobang', name: 'Cao Bằng' },
    { value: 'daklak', name: 'Đắk Lắk' },
    { value: 'daknong', name: 'Đắk Nông' },
    { value: 'dienbien', name: 'Điện Biên' },
    { value: 'dongnai', name: 'Đồng Nai' },
    { value: 'dongthap', name: 'Đồng Tháp' },
    { value: 'gialai', name: 'Gia Lai' },
    { value: 'hagiang', name: 'Hà Giang' },
    { value: 'hanam', name: 'Hà Nam' },
    { value: 'hatinh', name: 'Hà Tĩnh' },
    { value: 'haiduong', name: 'Hải Dương' },
    { value: 'haugiang', name: 'Hậu Giang' },
    { value: 'hoabinh', name: 'Hòa Bình' },
    { value: 'hungyen', name: 'Hưng Yên' },
    { value: 'khannhhoa', name: 'Khánh Hòa' },
    { value: 'kiengiang', name: 'Kiên Giang' },
    { value: 'kontum', name: 'Kon Tum' },
    { value: 'laichau', name: 'Lai Châu' },
    { value: 'lamdong', name: 'Lâm Đồng' },
    { value: 'langson', name: 'Lạng Sơn' },
    { value: 'laocai', name: 'Lào Cai' },
    { value: 'longan', name: 'Long An' },
    { value: 'namdinh', name: 'Nam Định' },
    { value: 'nghean', name: 'Nghệ An' },
    { value: 'ninhbinh', name: 'Ninh Bình' },
    { value: 'ninhthuan', name: 'Ninh Thuận' },
    { value: 'phutho', name: 'Phú Thọ' },
    { value: 'phuyen', name: 'Phú Yên' },
    { value: 'quangbinh', name: 'Quảng Bình' },
    { value: 'quangnam', name: 'Quảng Nam' },
    { value: 'quangngai', name: 'Quảng Ngãi' },
    { value: 'quangninh', name: 'Quảng Ninh' },
    { value: 'quangtri', name: 'Quảng Trị' },
    { value: 'soctrang', name: 'Sóc Trăng' },
    { value: 'sonla', name: 'Sơn La' },
    { value: 'tayninh', name: 'Tây Ninh' },
    { value: 'thaibinh', name: 'Thái Bình' },
    { value: 'thainguyen', name: 'Thái Nguyên' },
    { value: 'thanhhoa', name: 'Thanh Hóa' },
    { value: 'thuathienhue', name: 'Thừa Thiên Huế' },
    { value: 'tiengiang', name: 'Tiền Giang' },
    { value: 'travinh', name: 'Trà Vinh' },
    { value: 'tuyenquang', name: 'Tuyên Quang' },
    { value: 'vinhlong', name: 'Vĩnh Long' },
    { value: 'vinhphuc', name: 'Vĩnh Phúc' },
    { value: 'yenbai', name: 'Yên Bái' },
  ];
  removeModalBackdrop(): void {
    const backdrop = document.querySelector('.modal-backdrop');
    if (backdrop) {
      backdrop.remove(); // Xóa backdrop
      document.body.classList.remove('modal-open');
    }
  }
}
