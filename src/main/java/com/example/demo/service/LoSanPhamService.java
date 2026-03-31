package com.example.demo.service;

import com.example.demo.dao.LoSanPhamDAO;
import com.example.demo.model.LoSanPham;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class LoSanPhamService {

    private final LoSanPhamDAO loSanPhamDAO;

    // ✅ Constructor injection – CHUẨN SPRING BOOT
    public LoSanPhamService(LoSanPhamDAO loSanPhamDAO) {
        this.loSanPhamDAO = loSanPhamDAO;
    }

    // ========================= CRUD =========================

    public boolean themLoSanPham(LoSanPham lo) {
        return loSanPhamDAO.insert(lo);
    }

    public boolean capNhatLoSanPham(LoSanPham lo) {
        return loSanPhamDAO.update(lo);
    }

    public boolean xoaLoSanPham(int idLo) {
        return loSanPhamDAO.delete(idLo);
    }

    public LoSanPham timLoTheoId(int idLo) {
        return loSanPhamDAO.findById(idLo);
    }

    public List<LoSanPham> layTatCaLoSanPham() {
        return loSanPhamDAO.findAll();
    }

    // ========================= NGÀY NHẬP =========================

    public List<LoSanPham> timTheoNgayNhap(LocalDate date) {
        return loSanPhamDAO.findByDate(date);
    }

    public List<LoSanPham> timTheoThangNhap(YearMonth yearMonth) {
        return loSanPhamDAO.findByMonth(yearMonth);
    }

    public List<LoSanPham> timTheoNamNhap(int year) {
        return loSanPhamDAO.findByYear(year);
    }

    // ========================= HẠN SỬ DỤNG =========================

    public List<LoSanPham> timTheoHanSuDung(LocalDate date) {
        return loSanPhamDAO.findByExpiryDate(date);
    }

    public List<LoSanPham> timTheoKhoangHan(LocalDate start, LocalDate end) {
        return loSanPhamDAO.findByExpiryBetween(start, end);
    }

    public List<LoSanPham> loSapHetHanTrong(int soNgay) {
        return loSanPhamDAO.findExpiringWithinDays(soNgay);
    }

    public List<LoSanPham> loHetHan() {
        return loSanPhamDAO.findExpired();
    }

    // ========================= SẢN PHẨM / PHIẾU NHẬP =========================

    public List<LoSanPham> timTheoSanPham(int idSp) {
        return loSanPhamDAO.findBySanPhamId(idSp);
    }

    public List<LoSanPham> timTheoSanPhamConHang(int idSp) {
        return loSanPhamDAO.findConHangBySanPhamId(idSp);
    }

    public List<LoSanPham> timTheoSanPhamSapHetHan(int idSp) {
        return loSanPhamDAO.findBySanPhamIdOrderByExpiryAsc(idSp);
    }

    public List<LoSanPham> timTheoPhieuNhap(int idPhieuNhap) {
        return loSanPhamDAO.findByPhieuNhapId(idPhieuNhap);
    }

    // ========================= TỒN KHO =========================

    public List<LoSanPham> layLoConHang() {
        return loSanPhamDAO.findConHang();
    }

    public boolean capNhatSoLuongCon(int idLo, int soLuongMoi) {
        return loSanPhamDAO.updateSoLuongCon(idLo, soLuongMoi);
    }

    public boolean tangSoLuongCon(int idLo, int soLuongThem) {
        return loSanPhamDAO.tangSoLuongCon(idLo, soLuongThem);
    }

    public boolean giamSoLuongCon(int idLo, int soLuongBot) {
        return loSanPhamDAO.giamSoLuongCon(idLo, soLuongBot);
    }
}