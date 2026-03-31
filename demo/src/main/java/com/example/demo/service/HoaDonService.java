package com.example.demo.service;

import com.example.demo.dao.HoaDonDAO;
import com.example.demo.dao.impl.ChiTietHoaDonDAOImpl;
import com.example.demo.dao.impl.HoaDonDAOImpl;
import com.example.demo.dao.impl.LoSanPhamDAOImpl;
import com.example.demo.dao.impl.SanPhamDAOImpl;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.example.demo.model.LoSanPham;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class HoaDonService {

    private final HoaDonDAO hoaDonDAO;
    private final ChiTietHoaDonDAOImpl chiTietHoaDonDAO;
    private final SanPhamDAOImpl sanPhamDAO;
    private final LoSanPhamDAOImpl loSanPhamDAO;

    public HoaDonService(
            HoaDonDAOImpl hoaDonDAO,
            ChiTietHoaDonDAOImpl chiTietHoaDonDAO,
            SanPhamDAOImpl sanPhamDAO,
            LoSanPhamDAOImpl loSanPhamDAO) {

        this.hoaDonDAO = hoaDonDAO;
        this.chiTietHoaDonDAO = chiTietHoaDonDAO;
        this.sanPhamDAO = sanPhamDAO;
        this.loSanPhamDAO = loSanPhamDAO;
    }

    // ========================= TẠO HÓA ĐƠN (TRANSACTION) =========================

    @Transactional
    public boolean taoHoaDon(
            HoaDon hoaDon,
            List<ChiTietHoaDon> dsChiTiet,
            List<LoSanPham> dsLoUpdate) {

        if (!validateThanhToan(hoaDon, dsChiTiet)) {
            return false;
        }

        // 1) Insert hóa đơn
        int idHoaDon = hoaDonDAO.insertAndReturnId(hoaDon);
        if (idHoaDon <= 0) {
            throw new RuntimeException("Lỗi lưu hóa đơn");
        }

        // 2) Insert chi tiết + trừ tồn sản phẩm
        for (ChiTietHoaDon ct : dsChiTiet) {
            ct.setId_hoa_don(idHoaDon);

            int idCT = chiTietHoaDonDAO.insertAndReturnId(ct);
            if (idCT <= 0) {
                throw new RuntimeException(
                        "Lỗi lưu chi tiết hóa đơn (id_sp=" + ct.getId_san_pham() + ")"
                );
            }

            boolean okSp = sanPhamDAO.giamSoLuong(ct.getId_san_pham(), ct.getSo_luong());
            if (!okSp) {
                throw new RuntimeException(
                        "Sản phẩm ID " + ct.getId_san_pham() + " không đủ tồn kho"
                );
            }
        }

        // 3) Trừ tồn LÔ (FEFO/FIFO tuỳ logic phía controller)
        if (dsLoUpdate != null) {
            for (LoSanPham lo : dsLoUpdate) {
                int idLo = lo.getId_lo();
                int soLuongGiam = lo.getSo_luong_nhap(); // dùng làm số lượng giảm (đúng như thiết kế ban đầu)

                boolean okLo = loSanPhamDAO.giamSoLuongCon(idLo, soLuongGiam);
                if (!okLo) {
                    throw new RuntimeException(
                            "Lỗi cập nhật lô sản phẩm (id_lo=" + idLo + ")"
                    );
                }
            }
        }

        return true;
    }

    // ========================= VALIDATE =========================

    private boolean validateThanhToan(HoaDon hoaDon, List<ChiTietHoaDon> dsChiTiet) {
        if (hoaDon == null) return false;
        if (dsChiTiet == null || dsChiTiet.isEmpty()) return false;

        for (ChiTietHoaDon ct : dsChiTiet) {
            if (ct.getId_san_pham() <= 0) return false;
            if (ct.getSo_luong() <= 0) return false;
            if (ct.getDon_gia() < 0) return false;

            double expected = ct.getSo_luong() * ct.getDon_gia();
            if (Math.abs(ct.getThanh_tien() - expected) > 0.0001) return false;
        }
        return true;
    }

    // ==================== CÁC HÀM ĐỌC / ĐƠN GIẢN ====================

    public boolean suaHoaDon(HoaDon hoaDon) {
        return hoaDonDAO.update(hoaDon);
    }

    public boolean xoaHoaDon(int idHoaDon) {
        return hoaDonDAO.delete(idHoaDon);
    }

    public HoaDon timHoaDonTheoId(int idHoaDon) {
        return hoaDonDAO.findById(idHoaDon);
    }

    public List<HoaDon> layTatCaHoaDon() {
        return hoaDonDAO.findAll();
    }

    public List<HoaDon> layHoaDonTheoNgay(LocalDate date) {
        return hoaDonDAO.findByDate(date);
    }

    public List<HoaDon> layHoaDonTheoThang(YearMonth yearMonth) {
        return hoaDonDAO.findByMonth(yearMonth);
    }

    public List<HoaDon> layHoaDonTheoNam(int year) {
        return hoaDonDAO.findByYear(year);
    }

    public double tinhTongDoanhThuTheoNgay(LocalDate date) {
        return hoaDonDAO.sumAmountByDate(date);
    }

    public double tinhTongDoanhThuTheoThang(YearMonth yearMonth) {
        return hoaDonDAO.sumAmountByMonth(yearMonth);
    }

    public double tinhTongDoanhThuTheoNam(int year) {
        return hoaDonDAO.sumAmountByYear(year);
    }

    public int tinhTongSoLuongBanTheoNgay(LocalDate date) {
        return hoaDonDAO.sumQuantityByDate(date);
    }

    public int tinhTongSoLuongBanTheoThang(YearMonth yearMonth) {
        return hoaDonDAO.sumQuantityByMonth(yearMonth);
    }

    public int tinhTongSoLuongBanTheoNam(int year) {
        return hoaDonDAO.sumQuantityByYear(year);
    }
}