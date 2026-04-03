package com.example.demo.model;

import java.time.LocalDate;

public class HoaDon {

    private int id_hoa_don;
    private int id_kh;
    private int id_nhanVien;
    private int id_khuyenMai;
    private LocalDate ngay_lap;
    private double tong_tien;
    private String ghi_chu;

    public HoaDon() {
    }

    public HoaDon(int id_hoa_don, int id_kh, int id_nhanVien, int id_khuyenMai,
                  LocalDate ngay_lap, double tong_tien, String ghi_chu) {
        this.id_hoa_don = id_hoa_don;
        this.id_kh = id_kh;
        this.id_nhanVien = id_nhanVien;
        this.id_khuyenMai = id_khuyenMai;
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

    public int getId_kh() {
        return id_kh;
    }

    public void setId_kh(int id_kh) {
        this.id_kh = id_kh;
    }

    public int getId_nhanVien() {
        return id_nhanVien;
    }

    public void setId_nhanVien(int id_nhanVien) {
        this.id_nhanVien = id_nhanVien;
    }

    public int getId_khuyenMai() {
        return id_khuyenMai;
    }

    public void setId_khuyenMai(int id_khuyenMai) {
        this.id_khuyenMai = id_khuyenMai;
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
                ", id_kh=" + id_kh +
                ", id_nhanVien=" + id_nhanVien +
                ", id_khuyenMai=" + id_khuyenMai +
                ", ngay_lap=" + ngay_lap +
                ", tong_tien=" + tong_tien +
                ", ghi_chu='" + ghi_chu + '\'' +
                '}';
    }
}