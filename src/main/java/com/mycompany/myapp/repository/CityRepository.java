package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.City; // Thay đổi theo tên lớp City trong dự án của bạn
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> { // City là entity class và Long là kiểu của id
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh ở đây nếu cần
}
