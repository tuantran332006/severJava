package com.example.demo.model;

import java.time.LocalDate;

public class HoaDon {

    private int id_hoa_don;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private KhuyenMai khuyenMai;
    private LocalDate ngay_lap;
    private double tong_tien;
    private String ghi_chu;

    public HoaDon() {}

    public HoaDon(int id_hoa_don, KhachHang khachHang, NhanVien nhanVien,
                  LocalDate ngay_lap, double tong_tien, String ghi_chu) {
        this.id_hoa_don = id_hoa_don;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.ngay_lap = ngay_lap;
        this.tong_tien = tong_tien;
        this.ghi_chu = ghi_chu;
    }

    public int getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(int id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
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

    public KhuyenMai getKhuyenMai() {return khuyenMai;}

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public LocalDate getNgay_lap() {
        return ngay_lap;
    }

    public void setNgay_lap(LocalDate ngay_lap) {
        this.ngay_lap = ngay_lap;
    }

    public double getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(double tong_tien) {
        this.tong_tien = tong_tien;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "id_hoa_don=" + id_hoa_don +
                ", khachHang=" + khachHang +
                ", nhanVien=" + nhanVien +
                ", ngay_lap=" + ngay_lap +
                ", tong_tien=" + tong_tien +
                ", ghi_chu='" + ghi_chu + '\'' +
                '}';
    }
}