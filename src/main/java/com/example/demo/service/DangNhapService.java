package com.example.demo.service;

import com.example.demo.dao.impl.UserDAOImpl;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DangNhapService {

    private final UserDAOImpl userDAO;

    public DangNhapService(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    public UserView dangNhap(String username, String passwordPlain) {
        Optional<User> opt = userDAO.findByUsername(username);
        if (opt.isEmpty()) return null;

        User dbUser = opt.get();

        boolean matched =
                dbUser.getPassword() != null &&
                        dbUser.getPassword().equals(passwordPlain);

        if (!matched) return null;

        return UserView.from(dbUser);
    }

    public boolean kiemTraTonTaiUsername(String username) {
        return userDAO.existsByUsername(username);
    }

    public boolean dangKy(User newUser, String plainPassword) {
        if (kiemTraTonTaiUsername(newUser.getUsername())) {
            return false;
        }

        newUser.setPassword(plainPassword);

        if (newUser.getVai_tro() == null || newUser.getVai_tro().isBlank()) {
            newUser.setVai_tro("NHANVIEN");
        }

        if (newUser.getId_nhan_vien() != null && newUser.getId_nhan_vien() <= 0) {
            newUser.setId_nhan_vien(null);
        }

        return userDAO.insert(newUser);
    }

    public static class UserView {
        private int id_user;
        private String username;
        private String vai_tro;
        private Integer id_nhan_vien;

        public int getId_user() { return id_user; }
        public String getUsername() { return username; }
        public String getVai_tro() { return vai_tro; }
        public Integer getId_nhan_vien() { return id_nhan_vien; }

        public static UserView from(User u) {
            UserView v = new UserView();
            v.id_user = u.getId_user();
            v.username = u.getUsername();
            v.vai_tro = u.getVai_tro();
            v.id_nhan_vien = u.getId_nhan_vien();
            return v;
        }
    }
}
