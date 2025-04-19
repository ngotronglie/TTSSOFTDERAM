package com.example.backend.repository;

import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Các phương thức truy vấn tùy chỉnh có thể thêm vào nếu cần
    User findByEmail(String email);
    User findByPhone(String phone);
}
