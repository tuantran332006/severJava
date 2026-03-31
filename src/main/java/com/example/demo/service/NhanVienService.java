package com.example.demo.service;

import com.example.demo.dao.NhanVienDAO;
import com.example.demo.model.NhanVien;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class NhanVienService {

    private final NhanVienDAO nhanVienDAO;

    // ✅ Constructor injection – CHUẨN SPRING BOOT
    public NhanVienService(NhanVienDAO nhanVienDAO) {
        this.nhanVienDAO = nhanVienDAO;
    }

    // ========================== THÊM ==========================

    public boolean themNhanVien(NhanVien nv) {
        return nhanVienDAO.insert(nv);
    }

    public int themNhanVienVaLayId(NhanVien nv) {
        return nhanVienDAO.insertAndReturnId(nv);
    }

    // ========================== CẬP NHẬT / XOÁ ==========================

    public boolean capNhatNhanVien(NhanVien nv) {
        return nhanVienDAO.update(nv);
    }

    public boolean xoaNhanVien(int idNhanVien) {
        return nhanVienDAO.delete(idNhanVien);
    }

    // ========================== TRUY VẤN ==========================

    public NhanVien timTheoId(int idNhanVien) {
        return nhanVienDAO.findById(idNhanVien);
    }

    public List<NhanVien> layTatCaNhanVien() {
        return nhanVienDAO.findAll();
    }

    // ========================== THEO THỜI GIAN ==========================

    public List<NhanVien> timTheoNgayVaoLam(LocalDate date) {
        return nhanVienDAO.findByDate(date);
    }

    public List<NhanVien> timTheoThangVaoLam(YearMonth yearMonth) {
        return nhanVienDAO.findByMonth(yearMonth);
    }

    public List<NhanVien> timTheoNamVaoLam(int year) {
        return nhanVienDAO.findByYear(year);
    }
}
