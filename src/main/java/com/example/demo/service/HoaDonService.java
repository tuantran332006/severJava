package com.example.demo.service;

import com.example.demo.dao.HoaDonDAO;
import com.example.demo.dao.impl.ChiTietHoaDonDAOImpl;
import com.example.demo.dao.impl.KhachHangDAOImpl;
import com.example.demo.dao.impl.LoSanPhamDAOImpl;
import com.example.demo.dao.impl.NhanVienDAOImpl;
import com.example.demo.dao.impl.SanPhamDAOImpl;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.example.demo.model.HoaDonDetailResponse;
import com.example.demo.model.KhachHang;
import com.example.demo.model.NhanVien;
import com.example.demo.model.SanPham;
import jakarta.validation.Valid;
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
    private final KhachHangDAOImpl khachHangDAO;
    private final NhanVienDAOImpl nhanVienDAO;
    public HoaDonService(HoaDonDAO hoaDonDAO,
                         ChiTietHoaDonDAOImpl chiTietHoaDonDAO,
                         SanPhamDAOImpl sanPhamDAO,
                         LoSanPhamDAOImpl loSanPhamDAO,
                         KhachHangDAOImpl khachHangDAO,
                         NhanVienDAOImpl nhanVienDAO) {
        this.hoaDonDAO = hoaDonDAO;
        this.chiTietHoaDonDAO = chiTietHoaDonDAO;
        this.sanPhamDAO = sanPhamDAO;
        this.loSanPhamDAO = loSanPhamDAO;
        this.khachHangDAO = khachHangDAO;
        this.nhanVienDAO = nhanVienDAO;
    }
    // ========================= Táº O HÃ“A ÄÆ N (TRANSACTION) =========================
//xong
    @Transactional
    public boolean taoHoaDon(
            HoaDon hoaDon,
            List<ChiTietHoaDon> dsChiTiet) {

        if (!validateThanhToan(hoaDon, dsChiTiet)) {
            return false;
        }

        for (ChiTietHoaDon check : dsChiTiet) {

            if (!loSanPhamDAO.checkLoSanPhamTonTaiTheoId(check.getId_lo_san_pham())) {

                throw new RuntimeException(
                        "LÃ´ sáº£n pháº©m ID "
                                + check.getId_lo_san_pham()
                                + " khÃ´ng tá»“n táº¡i"
                );
            }
        }
        // 1) Insert hÃ³a Ä‘Æ¡n
        int idHoaDon = hoaDonDAO.insertAndReturnId(hoaDon);
        if (idHoaDon <= 0) {
            throw new RuntimeException("Lá»—i lÆ°u hÃ³a Ä‘Æ¡n");
        }

        // 2) Insert chi tiáº¿t + trá»« tá»“n sáº£n pháº©m+ trá»« tá»“n lÃ´
        for (ChiTietHoaDon ct : dsChiTiet) {
            ct.setId_hoa_don(idHoaDon);

            int idCT = chiTietHoaDonDAO.insertAndReturnId(ct);
            if (idCT <= 0) {
                throw new RuntimeException(
                        "Lá»—i lÆ°u chi tiáº¿t hÃ³a Ä‘Æ¡n (id_sp=" + ct.getId_san_pham() + ")"
                );
            }

            boolean okSp = sanPhamDAO.giamSoLuong(ct.getId_san_pham(), ct.getSo_luong());
            if (!okSp) {
                throw new RuntimeException(
                        "Sáº£n pháº©m ID " + ct.getId_san_pham() + " khÃ´ng Ä‘á»§ tá»“n kho"
                );
            }
            boolean okLo = loSanPhamDAO.giamSoLuongCon(
                    ct.getId_lo_san_pham(),
                    ct.getSo_luong()
            );

            if (!okLo) {
                throw new RuntimeException(
                        "LÃ´ sáº£n pháº©m ID "
                                + ct.getId_lo_san_pham()
                                + " khÃ´ng Ä‘á»§ sá»‘ lÆ°á»£ng"
                );
            }
        }


        return true;
    }

    // ========================= VALIDATE =========================

    private boolean validateThanhToan(HoaDon hoaDon, List<ChiTietHoaDon> dsChiTiet) {
        if (hoaDon == null) return false;
        if (dsChiTiet == null || dsChiTiet.isEmpty()) return false;
        if (hoaDon.getId_nhanVien() == null || hoaDon.getId_nhanVien() <= 0) return false;
        if (hoaDon.getNgay_lap() == null) return false;
        for (ChiTietHoaDon ct : dsChiTiet) {
            if (ct.getId_san_pham() == null || ct.getId_san_pham() <= 0) return false;
            if (ct.getId_lo_san_pham() == null || ct.getId_lo_san_pham() <= 0) return false;
            if (ct.getSo_luong() == null || ct.getSo_luong() <= 0) return false;
            if (ct.getSo_luong() > loSanPhamDAO.checkSoLuongSPConTheoId(ct.getId_lo_san_pham())) return false;
            if (ct.getDon_gia() < 0) return false;
        }
        return true;
    }

    // ==================== CÃC HÃ€M Äá»ŒC / ÄÆ N GIáº¢N ====================

    public boolean suaHoaDon(HoaDon hoaDon) {
        return hoaDonDAO.update(hoaDon);
    }

    public boolean xoaHoaDon(int idHoaDon) {
        return hoaDonDAO.delete(idHoaDon);
    }

    public HoaDon timHoaDonTheoId(int idHoaDon) {
        return hoaDonDAO.findById(idHoaDon);
    }

    public HoaDonDetailResponse layChiTietHoaDon(int idHoaDon) {
        HoaDon hoaDon = hoaDonDAO.findById(idHoaDon);
        if (hoaDon == null) {
            return null;
        }

        HoaDonDetailResponse response = new HoaDonDetailResponse();
        response.setHoaDon(hoaDon);

        if (hoaDon.getId_kh() != null) {
            KhachHang khachHang = khachHangDAO.findById(hoaDon.getId_kh());
            response.setKhachHang(khachHang);
        }

        if (hoaDon.getId_nhanVien() != null) {
            NhanVien nhanVien = nhanVienDAO.findById(hoaDon.getId_nhanVien());
            response.setNhanVien(nhanVien);
        }

        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonDAO.findByHoaDonId(idHoaDon);
        List<HoaDonDetailResponse.ChiTietItem> items = new java.util.ArrayList<>();
        for (ChiTietHoaDon chiTiet : chiTietHoaDonList) {
            HoaDonDetailResponse.ChiTietItem item = new HoaDonDetailResponse.ChiTietItem();
            item.setId_chi_tiet(chiTiet.getId_chi_tiet());
            item.setId_lo_san_pham(chiTiet.getId_lo_san_pham());
            item.setId_san_pham(chiTiet.getId_san_pham());
            item.setSo_luong(chiTiet.getSo_luong());
            item.setDon_gia(chiTiet.getDon_gia());
            item.setThanh_tien(chiTiet.getThanh_tien());

            if (chiTiet.getId_san_pham() != null) {
                SanPham sanPham = sanPhamDAO.findById(chiTiet.getId_san_pham());
                if (sanPham != null) {
                    item.setTen_san_pham(sanPham.getTenSp());
                }
            }

            items.add(item);
        }
        response.setChiTietList(items);

        return response;
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
