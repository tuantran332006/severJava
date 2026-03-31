package com.example.demo.service;

import com.example.demo.dao.ChiTietPhieuNhapDAO;
import com.example.demo.dao.LoSanPhamDAO;
import com.example.demo.dao.PhieuNhapDAO;
import com.example.demo.dao.SanPhamDAO;
import com.example.demo.model.ChiTietPhieuNhap;
import com.example.demo.model.LoSanPham;
import com.example.demo.model.PhieuNhap;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class PhieuNhapService {

    private final PhieuNhapDAO phieuNhapDAO;
    private final ChiTietPhieuNhapDAO chiTietPhieuNhapDAO;
    private final SanPhamDAO sanPhamDAO;
    private final LoSanPhamDAO loSanPhamDAO;

    public PhieuNhapService(
            PhieuNhapDAO phieuNhapDAO,
            ChiTietPhieuNhapDAO chiTietPhieuNhapDAO,
            SanPhamDAO sanPhamDAO,
            LoSanPhamDAO loSanPhamDAO) {

        this.phieuNhapDAO = phieuNhapDAO;
        this.chiTietPhieuNhapDAO = chiTietPhieuNhapDAO;
        this.sanPhamDAO = sanPhamDAO;
        this.loSanPhamDAO = loSanPhamDAO;
    }

    // ========================= TẠO PHIẾU NHẬP =========================

    @Transactional
    public boolean taoPhieuNhap(
            PhieuNhap phieuNhap,
            List<ChiTietPhieuNhap> chiTietList,
            boolean taoLoTuChiTiet) {

        if (!validatePhieuNhap(phieuNhap, chiTietList)) {
            return false;
        }

        int idPhieuNhap = phieuNhapDAO.insertAndReturnId(phieuNhap);
        if (idPhieuNhap <= 0) {
            throw new RuntimeException("Insert phieu_nhap thất bại");
        }

        for (ChiTietPhieuNhap ct : chiTietList) {

            ct.setId_phieu_nhap(idPhieuNhap);

            int idCt = chiTietPhieuNhapDAO.insertAndReturnId(ct);
            if (idCt <= 0) {
                throw new RuntimeException(
                        "Insert chi_tiet_phieu_nhap thất bại (id_sp=" + ct.getId_sp() + ")"
                );
            }

            if (!sanPhamDAO.tangSoLuong(ct.getId_sp(), ct.getSo_luong())) {
                throw new RuntimeException(
                        "Tăng tồn kho sản phẩm thất bại (id_sp=" + ct.getId_sp() + ")"
                );
            }

            if (taoLoTuChiTiet) {
                LoSanPham lo = new LoSanPham();
                lo.setId_sp(ct.getId_sp());
                lo.setId_phieu_nhap(idPhieuNhap);
                lo.setSo_luong_nhap(ct.getSo_luong());
                lo.setSo_luong_con(ct.getSo_luong());
                lo.setDon_gia_nhap(ct.getDon_gia_nhap());

                LocalDateTime ngayNhap =
                        phieuNhap.getNgay_nhap() != null
                                ? phieuNhap.getNgay_nhap()
                                : LocalDateTime.now();

                lo.setNgay_nhap(ngayNhap);

                int idLo = loSanPhamDAO.insertAndReturnId(lo);
                if (idLo <= 0) {
                    throw new RuntimeException("Tạo lô sản phẩm thất bại");
                }
            }
        }

        return true;
    }

    // ========================= XOÁ PHIẾU NHẬP =========================

    @Transactional
    public boolean xoaPhieuNhap(int idPhieuNhap, boolean restoreInventoryOnDelete) {

        if (idPhieuNhap <= 0) return false;

        List<ChiTietPhieuNhap> chiTietList =
                chiTietPhieuNhapDAO.findByPhieuNhapId(idPhieuNhap);

        if (restoreInventoryOnDelete) {
            for (ChiTietPhieuNhap ct : chiTietList) {
                if (!sanPhamDAO.giamSoLuong(ct.getId_sp(), ct.getSo_luong())) {
                    throw new RuntimeException(
                            "Khôi phục tồn kho thất bại (id_sp=" + ct.getId_sp() + ")"
                    );
                }
            }
        }

        for (ChiTietPhieuNhap ct : chiTietList) {
            if (!chiTietPhieuNhapDAO.delete(ct.getId_chi_tiet_nhap())) {
                throw new RuntimeException(
                        "Xoá chi tiết phiếu nhập thất bại (id=" + ct.getId_chi_tiet_nhap() + ")"
                );
            }
        }

        if (!phieuNhapDAO.delete(idPhieuNhap)) {
            throw new RuntimeException(
                    "Xoá phiếu nhập thất bại (id=" + idPhieuNhap + ")"
            );
        }

        return true;
    }

    // ========================= VALIDATE =========================

    private boolean validatePhieuNhap(PhieuNhap pn, List<ChiTietPhieuNhap> ctList) {

        if (pn == null || ctList == null || ctList.isEmpty()) return false;
        if (pn.getId_ncc() <= 0 || pn.getId_nhan_vien() <= 0) return false;
        if (pn.getTong_tien() < 0) return false;

        for (ChiTietPhieuNhap ct : ctList) {
            if (ct.getId_sp() <= 0 || ct.getSo_luong() <= 0 || ct.getDon_gia_nhap() < 0)
                return false;

            double expected = ct.getSo_luong() * ct.getDon_gia_nhap();
            if (Math.abs(ct.getThanh_tien() - expected) > 0.0001)
                return false;
        }
        return true;
    }

    // ========================= TRUY VẤN =========================

    public PhieuNhap timPhieuNhapTheoId(int id) {
        return phieuNhapDAO.findById(id);
    }

    public List<PhieuNhap> layTatCaPhieuNhap() {
        return phieuNhapDAO.findAll();
    }

    public List<PhieuNhap> layPhieuNhapTheoNgay(LocalDate date) {
        return phieuNhapDAO.findByDate(date);
    }

    public List<PhieuNhap> layPhieuNhapTheoThang(YearMonth ym) {
        return phieuNhapDAO.findByMonth(ym);
    }

    public List<PhieuNhap> layPhieuNhapTheoNam(int year) {
        return phieuNhapDAO.findByYear(year);
    }

 // ========================= THỐNG KÊ TIỀN NHẬP =========================

    public double tinhTongTienNhapTheoNgay(LocalDate date) {
        return phieuNhapDAO.sumAmountByDate(date);
    }

    public double tinhTongTienNhapTheoThang(YearMonth yearMonth) {
        return phieuNhapDAO.sumAmountByMonth(yearMonth);
    }

    public double tinhTongTienNhapTheoNam(
            @Min(1970) @Max(3000) int year) {
        return phieuNhapDAO.sumAmountByYear(year);
    }
    
}