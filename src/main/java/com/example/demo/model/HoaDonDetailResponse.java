package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDetailResponse {

    private HoaDon hoaDon;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private List<ChiTietItem> chiTietList = new ArrayList<>();

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public List<ChiTietItem> getChiTietList() {
        return chiTietList;
    }

    public void setChiTietList(List<ChiTietItem> chiTietList) {
        this.chiTietList = chiTietList;
    }

    public static class ChiTietItem {
        private Integer id_chi_tiet;
        private Integer id_lo_san_pham;
        private Integer id_san_pham;
        private String ten_san_pham;
        private Integer so_luong;
        private double don_gia;
        private double thanh_tien;

        public Integer getId_chi_tiet() {
            return id_chi_tiet;
        }

        public void setId_chi_tiet(Integer id_chi_tiet) {
            this.id_chi_tiet = id_chi_tiet;
        }

        public Integer getId_lo_san_pham() {
            return id_lo_san_pham;
        }

        public void setId_lo_san_pham(Integer id_lo_san_pham) {
            this.id_lo_san_pham = id_lo_san_pham;
        }

        public Integer getId_san_pham() {
            return id_san_pham;
        }

        public void setId_san_pham(Integer id_san_pham) {
            this.id_san_pham = id_san_pham;
        }

        public String getTen_san_pham() {
            return ten_san_pham;
        }

        public void setTen_san_pham(String ten_san_pham) {
            this.ten_san_pham = ten_san_pham;
        }

        public Integer getSo_luong() {
            return so_luong;
        }

        public void setSo_luong(Integer so_luong) {
            this.so_luong = so_luong;
        }

        public double getDon_gia() {
            return don_gia;
        }

        public void setDon_gia(double don_gia) {
            this.don_gia = don_gia;
        }

        public double getThanh_tien() {
            return thanh_tien;
        }

        public void setThanh_tien(double thanh_tien) {
            this.thanh_tien = thanh_tien;
        }
    }
}
