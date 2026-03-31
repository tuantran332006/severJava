package com.example.demo.daointerface;

import com.example.demo.model.User;

import java.util.List;

public interface UserDAOInterface  {
    // CRUD sẵn có...
    boolean insert(User entity);
    boolean update(User entity);
    boolean delete(Integer id);
    User findById(Integer id);
    List<User> findAll();

    // Bổ sung
    java.util.Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    // (tùy chọn) cập nhật mật khẩu đã băm
    boolean updatePassword(int idUser, String hashedPassword);
}
