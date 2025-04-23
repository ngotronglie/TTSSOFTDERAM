package com.example.backend.repository;

import com.example.backend.dto.UserTDO;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Các phương thức truy vấn tùy chỉnh có thể thêm vào nếu cần
    User findByEmail(String email);
    User findByPhone(String phone);
    // Sử dụng phương thức tìm kiếm người dùng bằng email và mật khẩu
    @Query("SELECT new com.example.backend.dto.UserTDO(u.id_user, u.firstname, u.lastname, u.email, u.avatar, u.role_id) FROM User u WHERE u.email = :email AND u.password = :password")
    Optional<UserTDO> findByEmailAndPassword(String email, String password);

}
