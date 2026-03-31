package com.example.demo.service;

import com.example.demo.dao.SanPhamDAO;
import com.example.demo.model.SanPham;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamService {

    private final SanPhamDAO sanPhamDAO;

    // ✅ Constructor injection – CHUẨN SPRING BOOT
    public SanPhamService(SanPhamDAO sanPhamDAO) {
        this.sanPhamDAO = sanPhamDAO;
    }

    // ========================== THÊM SẢN PHẨM ==========================

    /** Thêm sản phẩm; nếu thành công sẽ set id vào entity */
    public boolean themSanPham(SanPham sp) {
        return sanPhamDAO.insert(sp);
    }

    /** Thêm sản phẩm và trả về ID tự tăng */
    public int themSanPhamVaLayId(SanPham sp) {
        Integer id = sanPhamDAO.insertAndReturnId(sp);
        return id != null ? id : -1;
    }

    // ========================== CẬP NHẬT / XOÁ ==========================

    public boolean capNhatSanPham(SanPham sp) {
        return sanPhamDAO.update(sp);
    }

    public boolean xoaSanPham(int idSp) {
        return sanPhamDAO.delete(idSp);
    }

    // ========================== TRUY VẤN CƠ BẢN ==========================

    public SanPham timTheoId(int idSp) {
        return sanPhamDAO.findById(idSp);
    }

    public List<SanPham> layTatCa() {
        return sanPhamDAO.findAll();
    }

    // ========================== TRUY VẤN NÂNG CAO ==========================

    public List<SanPham> timTheoLoai(int idLoai) {
        return sanPhamDAO.findByLoai(idLoai);
    }

    public List<SanPham> timTheoTen(String tenSp) {
        return sanPhamDAO.findByTen(tenSp);
    }

    public List<SanPham> timTheoKhoangGia(double giaMin, double giaMax) {
        return sanPhamDAO.findByKhoangGia(giaMin, giaMax);
    }

    public boolean tonTaiTheoId(int idSp) {
        return sanPhamDAO.existsById(idSp);
    }

    // ========================== TỒN KHO ==========================

    /** Cập nhật trực tiếp tổng số lượng tồn kho */
    public boolean capNhatTongSoLuong(int idSp, int tongSoLuongMoi) {
        return sanPhamDAO.updateTongSoLuong(idSp, tongSoLuongMoi);
    }

    /** Tăng số lượng tồn kho */
    public boolean tangSoLuong(int idSp, int soLuongThem) {
        return sanPhamDAO.tangSoLuong(idSp, soLuongThem);
    }

    /** Giảm số lượng tồn kho (chỉ trừ khi tồn kho đủ) */
    public boolean giamSoLuong(int idSp, int soLuongBot) {
        return sanPhamDAO.giamSoLuong(idSp, soLuongBot);
    }
}