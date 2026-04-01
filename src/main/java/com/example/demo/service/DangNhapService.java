package com.example.demo.service;

import com.example.demo.dao.impl.NhanVienDAOImpl;
import com.example.demo.dao.impl.UserDAOImpl;
import com.example.demo.model.NhanVien;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DangNhapService {

    private final UserDAOImpl userDAO;
    private final NhanVienDAOImpl nhanVienDAO;

    public DangNhapService(UserDAOImpl userDAO, NhanVienDAOImpl nhanVienDAO) {
        this.userDAO = userDAO;
        this.nhanVienDAO = nhanVienDAO;
    }
    public UserView dangKyVaTraUser(User newUser, String plainPassword) {
        if (kiemTraTonTaiUsername(newUser.getUsername())) {
            return null;
        }

        // tạo nhân viên
        NhanVien nv = new NhanVien();
        nv.setHo_ten("Chưa cập nhật");
        nv.setNgay_vao_lam(LocalDateTime.now());

        Integer idNhanVien = nhanVienDAO.insertAndReturnId(nv);
        if (idNhanVien == null) {
            throw new RuntimeException("Không tạo được nhân viên");
        }

        newUser.setPassword(plainPassword);
        newUser.setVai_tro("NHANVIEN");
        newUser.setId_nhan_vien(idNhanVien);

        boolean inserted = userDAO.insert(newUser);

        if (!inserted) return null;

        return UserView.from(newUser);
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


    public static class UserView {
        private Integer id_user;
        private String username;
        private String vai_tro;
        private Integer id_nhan_vien;

        public Integer getId_user() { return id_user; }
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