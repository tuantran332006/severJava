package com.example.demo.service;

import com.example.demo.dao.ChiTietHoaDonDAO;
import com.example.demo.dao.HoaDonDAO;
import com.example.demo.dao.LoSanPhamDAO;
import com.example.demo.dao.SanPhamDAO;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.example.demo.model.LoSanPham;
import com.example.demo.model.SanPham;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class HoaDonService {

    private final HoaDonDAO hoaDonDAO;
    private final ChiTietHoaDonDAO chiTietHoaDonDAO;
    private final SanPhamDAO sanPhamDAO;
    private final LoSanPhamDAO loSanPhamDAO;

    public HoaDonService(
            HoaDonDAO hoaDonDAO,
            ChiTietHoaDonDAO chiTietHoaDonDAO,
            SanPhamDAO sanPhamDAO,
            LoSanPhamDAO loSanPhamDAO) {
        this.hoaDonDAO = hoaDonDAO;
        this.chiTietHoaDonDAO = chiTietHoaDonDAO;
        this.sanPhamDAO = sanPhamDAO;
        this.loSanPhamDAO = loSanPhamDAO;
    }

    @Transactional
    public boolean taoHoaDon(HoaDon hoaDon, List<ChiTietHoaDon> dsChiTiet) {
        if (!validateThanhToan(hoaDon, dsChiTiet)) {
            return false;
        }

        // 1. Kiểm tra tồn kho trước
        for (ChiTietHoaDon ct : dsChiTiet) {
            SanPham sp = sanPhamDAO.findById(ct.getId_san_pham());
            if (sp == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm ID " + ct.getId_san_pham());
            }

            Integer tonKho = sp.getTongSoLuongSpTrongKho();
            if (tonKho == null || tonKho < ct.getSo_luong()) {
                throw new RuntimeException("Sản phẩm ID " + ct.getId_san_pham() + " không đủ tồn kho");
            }

            List<LoSanPham> dsLo = loSanPhamDAO.findConHangBySanPhamId(ct.getId_san_pham());
            int tongTonTheoLo = dsLo.stream().mapToInt(LoSanPham::getSo_luong_con).sum();

            if (tongTonTheoLo < ct.getSo_luong()) {
                throw new RuntimeException("Tổng tồn theo lô không đủ cho sản phẩm ID " + ct.getId_san_pham());
            }
        }

        // 2. Lưu hóa đơn
        Integer idHoaDon = hoaDonDAO.insertAndReturnId(hoaDon);
        if (idHoaDon == null || idHoaDon <= 0) {
            throw new RuntimeException("Lỗi lưu hóa đơn");
        }
        hoaDon.setId_hoa_don(idHoaDon);

        // 3. Lưu chi tiết + trừ tồn theo lô + giảm tồn tổng
        for (ChiTietHoaDon ct : dsChiTiet) {
            ct.setId_hoa_don(idHoaDon);

            Integer idCT = chiTietHoaDonDAO.insertAndReturnId(ct);
            if (idCT == null || idCT <= 0) {
                throw new RuntimeException("Lỗi lưu chi tiết hóa đơn cho sản phẩm ID " + ct.getId_san_pham());
            }
            ct.setId_chi_tiet(idCT);

            truTonTheoLo(ct.getId_san_pham(), ct.getSo_luong());

            boolean okSp = sanPhamDAO.giamSoLuong(ct.getId_san_pham(), ct.getSo_luong());
            if (!okSp) {
                throw new RuntimeException("Lỗi giảm tồn tổng sản phẩm ID " + ct.getId_san_pham());
            }
        }

        return true;
    }

    private void truTonTheoLo(int idSp, int soLuongCanTru) {
        List<LoSanPham> dsLo = loSanPhamDAO.findConHangBySanPhamId(idSp);

        int conPhaiTru = soLuongCanTru;

        for (LoSanPham lo : dsLo) {
            if (conPhaiTru <= 0) {
                break;
            }

            int slCon = lo.getSo_luong_con();
            if (slCon <= 0) {
                continue;
            }

            int soLuongTruThucTe = Math.min(slCon, conPhaiTru);

            boolean okLo = loSanPhamDAO.giamSoLuongCon(lo.getId_lo(), soLuongTruThucTe);
            if (!okLo) {
                throw new RuntimeException("Lỗi cập nhật lô sản phẩm ID lô = " + lo.getId_lo());
            }

            conPhaiTru -= soLuongTruThucTe;
        }

        if (conPhaiTru > 0) {
            throw new RuntimeException("Không đủ tồn kho theo lô cho sản phẩm ID " + idSp);
        }
    }

    private boolean validateThanhToan(HoaDon hoaDon, List<ChiTietHoaDon> dsChiTiet) {
        if (hoaDon == null) return false;
        if (dsChiTiet == null || dsChiTiet.isEmpty()) return false;

        if (hoaDon.getKhachHang() == null || hoaDon.getKhachHang().getId_kh() <= 0) return false;
        if (hoaDon.getNhanVien() == null || hoaDon.getNhanVien().getId_nhan_vien() <= 0) return false;
        if (hoaDon.getNgay_lap() == null) return false;

        double tongChiTiet = 0.0;

        for (ChiTietHoaDon ct : dsChiTiet) {
            if (ct == null) return false;
            if (ct.getId_san_pham() <= 0) return false;
            if (ct.getSo_luong() <= 0) return false;
            if (ct.getDon_gia() < 0) return false;

            double expected = ct.getSo_luong() * ct.getDon_gia();
            if (Math.abs(ct.getThanh_tien() - expected) > 0.0001) return false;

            tongChiTiet += ct.getThanh_tien();
        }

        double giamGia = 0.0;
        if (hoaDon.getKhuyenMai() != null) {
            giamGia = hoaDon.getKhuyenMai().getPhan_tram_giam();
            if (giamGia < 0 || giamGia > 100) return false;
        }

        double tongSauGiam = tongChiTiet * (1 - giamGia / 100.0);
        return Math.abs(hoaDon.getTong_tien() - tongSauGiam) <= 0.0001;
    }

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