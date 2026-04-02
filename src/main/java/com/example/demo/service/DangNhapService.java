package com.example.demo.service;

import com.example.demo.dao.impl.NhanVienDAOImpl;
import com.example.demo.dao.impl.UserDAOImpl;
import com.example.demo.model.NhanVien;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

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

    public UserView dangNhap(String username, String passwordPlain) {

        Optional<User> opt = userDAO.findByUsername(username);
        if (opt.isEmpty()) return null;

        User dbUser = opt.get();

        // ===== TẠM THỜI plaintext (CHỈ học, KHÔNG production) =====
        boolean matched =
                dbUser.getPassword() != null &&
                        dbUser.getPassword().equals(passwordPlain);


        if (!matched) return null;

        return UserView.from(dbUser);
    }

    /** Kiểm tra username đã tồn tại */
    public Boolean kiemTraTonTaiUsername(String username){
        return userDAO.existsByUsername(username);
    }
    public boolean kiemTraTonTaiUserid(int userid) {
        return !userDAO.existsById(userid);
    }
    public boolean dangKy(User newUser,NhanVien nhanVien, String plainPassword) {

        // tạo nhân viên
        NhanVien nv = new NhanVien();

        nv.setHo_ten(nhanVien.getHo_ten());
        nv.setNgay_vao_lam(LocalDateTime.now());
        nv.setChuc_vu(nhanVien.getChuc_vu());
        nv.setLuong(nhanVien.getLuong());
        nv.setDia_chi(nhanVien.getDia_chi());
        nv.setSo_dien_thoai(nhanVien.getSo_dien_thoai());
        nv.setTuoi(nhanVien.getTuoi());
        Integer idNhanVien = nhanVienDAO.insertAndReturnId(nv);
        if (idNhanVien == null) {
            throw new RuntimeException("Không tạo được nhân viên");
        }

        newUser.setPassword(plainPassword);
        newUser.setId_nhan_vien(idNhanVien);
        int iduser=userDAO.insertAndReturnId(newUser);
        boolean inserted = userDAO.insert(newUser);

        if (!inserted) return false;

        return true;
    }

    // ================= DTO an toàn =================

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
