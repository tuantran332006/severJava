package com.example.demo.service;

import com.example.demo.dao.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class ThongKeService {

    private final HoaDonDAO hoaDonDAO;
    private final PhieuNhapDAO phieuNhapDAO;
    private final ChiTietHoaDonDAO chiTietHoaDonDAO;
    private final ChiTietPhieuNhapDAO chiTietPhieuNhapDAO;
    private final KhachHangDAO khachHangDAO;

    // ✅ Constructor injection – Spring tự động inject Impl
    public ThongKeService(
            HoaDonDAO hoaDonDAO,
            PhieuNhapDAO phieuNhapDAO,
            ChiTietHoaDonDAO chiTietHoaDonDAO,
            ChiTietPhieuNhapDAO chiTietPhieuNhapDAO,
            KhachHangDAO khachHangDAO) {

        this.hoaDonDAO = hoaDonDAO;
        this.phieuNhapDAO = phieuNhapDAO;
        this.chiTietHoaDonDAO = chiTietHoaDonDAO;
        this.chiTietPhieuNhapDAO = chiTietPhieuNhapDAO;
        this.khachHangDAO = khachHangDAO;
    }

    // ================== DOANH THU ==================

    public double tinhDoanhThuNgay(LocalDate date) {
        return hoaDonDAO.sumAmountByDate(date);
    }

    public double tinhDoanhThuThang(YearMonth yearMonth) {
        return hoaDonDAO.sumAmountByMonth(yearMonth);
    }

    public double tinhDoanhThuNam(int year) {
        return hoaDonDAO.sumAmountByYear(year);
    }

    // ================== TIỀN NHẬP ==================

    public double tinhTienNhapNgay(LocalDate date) {
        return phieuNhapDAO.sumAmountByDate(date);
    }

    public double tinhTienNhapThang(YearMonth yearMonth) {
        return phieuNhapDAO.sumAmountByMonth(yearMonth);
    }

    public double tinhTienNhapNam(int year) {
        return phieuNhapDAO.sumAmountByYear(year);
    }

    // ================== SỐ LƯỢNG BÁN ==================

    public int tinhSoLuongBanNgay(LocalDate date) {
        return chiTietHoaDonDAO.sumQuantityByDate(date);
    }

    public int tinhSoLuongBanThang(YearMonth yearMonth) {
        return chiTietHoaDonDAO.sumQuantityByMonth(yearMonth);
    }

 // ================== SỐ LƯỢNG NHẬP ==================

    public int tinhSoLuongNhapNgay(LocalDate date) {
        return chiTietPhieuNhapDAO.sumQuantityByDate(date);
    }

    public int tinhSoLuongNhapThang(YearMonth yearMonth) {
        return chiTietPhieuNhapDAO.sumQuantityByMonth(yearMonth);
    }

    public int tinhSoLuongNhapNam(int year) {
        return chiTietPhieuNhapDAO.sumQuantityByYear(year);
    }
    // ================== TIỀN TỪ CHI TIẾT ==================

    public double tinhTienBanTuChiTietNgay(LocalDate date) {
        return chiTietHoaDonDAO.sumAmountByDate(date);
    }

    public double tinhTienNhapTuChiTietNgay(LocalDate date) {
        return chiTietPhieuNhapDAO.sumAmountByDate(date);
    }

    // ================== LỢI NHUẬN ==================

    public double tinhLoiNhuanNgay(LocalDate date) {
        return tinhDoanhThuNgay(date) - tinhTienNhapNgay(date);
    }

    public double tinhLoiNhuanThang(YearMonth yearMonth) {
        return tinhDoanhThuThang(yearMonth) - tinhTienNhapThang(yearMonth);
    }

    public double tinhLoiNhuanNam(int year) {
        return tinhDoanhThuNam(year) - tinhTienNhapNam(year);
    }
}